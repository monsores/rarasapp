package com.rarasnet.rnp.desordens.profile.description;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ronnyerybarbosa.library.MaterialDialog;
import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.profile.DisorderProfileActivity;
import com.rarasnet.rnp.desordens.search.models.DisorderProfile;
import com.rarasnet.rnp.shared.util.managers.PDFManager;
import com.rarasnet.rnp.shared.util.managers.ProtocolsManager;

/**
 * Created by Farina on 16/10/2015.
 */
public class DescriptionFragment  extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private TextView tv_orpha;
    private TextView tv_cid;
    private TextView tv_expertlink;
    private TextView tv_descricao;
    private TextView tv_nome;
    private TextView tv_protocolo;


    private String downloadedPdfName;
    private String downloadedPdfId;
    private PDFReceiver pdfBroadcastReceiver;
    private ProtocolsManager pManager;

    private DisorderProfile disorderProfile = DisorderProfileActivity.mDisorderProfile;

    MaterialDialog mMaterialDiaolg;

    public static DescriptionFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DescriptionFragment fragment = new DescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final DisorderProfile disorderProfile = DisorderProfileActivity.mDisorderProfile;
        final String diseaseExpertlink = disorderProfile.getDisorder().getExpertlink();
        final String diseaseOrphanumber = disorderProfile.getDisorder().getOrphanumber();
        final String descricao = disorderProfile.getDisorder().getDescricao();

        final String cid =disorderProfile.getCid().getCid();



        //private DadosNacionais dadosNacionais;

        RelativeLayout view  = (RelativeLayout) inflater.inflate(R.layout.fragment_disorder_description, container, false);

        tv_nome = (TextView) view.findViewById(R.id.frag_disorder_description_tv_titleValue);
        tv_nome.setText(disorderProfile.getDisorder().getName());

        tv_orpha = (TextView) view.findViewById(R.id.frag_disorder_description_tv_orphaValue);
        tv_orpha.setText(diseaseOrphanumber);

        tv_cid = (TextView) view.findViewById(R.id.frag_disorder_description_tv_cidValue);
        if(disorderProfile.getCid().getCid() != null) {
            tv_cid.setText(disorderProfile.getCid().getCid());
        }else {
            tv_cid.setText("Não cadastrado.");
        }

//        tv_expertlink = (TextView) view.findViewById(R.id.frag_disorder_description_tv_expertlink);
//        tv_expertlink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                init(v);
//                show("Consultar Orphanet", "Deseja consulta doença " +disorderProfile.getDadosNacionais().getDoenca()+ " no Orphanet","","");
//
//            }
//        });

        tv_descricao = (TextView) view.findViewById(R.id.frag_disorder_description_tv_descricao);

        if(descricao == null || descricao.isEmpty()) {
            tv_descricao.setText("Sem descrição");
        } else {
            tv_descricao.setText(descricao);
        }

//        tv_protocolo = (TextView) view.findViewById(R.id.frag_disorder_description_tv_download);
//        tv_protocolo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                init(v);
//              show("Baixar Protocolo","Deseja Baixar protocolo de",disorderProfile.getDadosNacionais().getDoenca(),"pdf");
//               /* if(disorderProfile.getDadosNacionais().getProtocolo() == null){
//
//                }else {
//                    if(PDFManager.hasFileInLocal(disorderProfile.getDadosNacionais().getProtocolo())) {
//                        openPDF(Uri.parse(pManager.getLocalPDFPath(disorderProfile.getDadosNacionais().getProtocolo())));
//                    } else {
//                        Log.d("nome",disorderProfile.getDadosNacionais().getProtocolo());
//                        Log.d("id",disorderProfile.getDadosNacionais().getId());
//                        downloadedPdfName = disorderProfile.getDadosNacionais().getProtocolo();
//                        Log.d("nome",downloadedPdfName);
//                        pManager.downloadPDF(disorderProfile.getDadosNacionais().getProtocolo(), disorderProfile.getDadosNacionais().getId());
//                    }
//                }*/
//            }
//        });




        return view;
    }


    public void downloadPdf(){
        if(disorderProfile.getDadosNacionais().getProtocolo() == null){

        }else {
            if(PDFManager.hasFileInLocal(disorderProfile.getDadosNacionais().getProtocolo())) {
                openPDF(Uri.parse(pManager.getLocalPDFPath(disorderProfile.getDadosNacionais().getProtocolo())));
            } else {
                Log.d("nome",disorderProfile.getDadosNacionais().getProtocolo());
                Log.d("id",disorderProfile.getDadosNacionais().getId());
                downloadedPdfName = disorderProfile.getDadosNacionais().getProtocolo();
                Log.d("nome",downloadedPdfName);
                pManager.downloadPDF(disorderProfile.getDadosNacionais().getProtocolo(), disorderProfile.getDadosNacionais().getId());
            }
        }

    }

    public void consultaOrphanet(){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(disorderProfile.getDisorder().getExpertlink()));
        startActivity(i);
    }

    public void openPDFThroughGDrive(final String pdfUrl) {
        Log.d("num","1");
        Intent i = new Intent( Intent.ACTION_VIEW );
        i.setDataAndType(Uri.parse(pManager.getGoogleDriveReaderPrefix() + pdfUrl),
                pManager.getHtmlMimeType());
        startActivity(i);
    }

    private void openPDF(Uri localUri ) {
        Log.d("num","2");
        Intent i = new Intent(Intent.ACTION_VIEW );
        i.setDataAndType(localUri, pManager.getPdfMimeType());
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void askToOpenPDFThroughGoogleDrive(final String pdfUrl ) {
        Log.d("num","3");
        new AlertDialog.Builder(this.getActivity())
                .setTitle("Aviso")
                .setMessage("Nennhum leitor de Pdfs foi encontrado neste aparelho. Deseja " +
                        "abrir o arquivo no Google Drive?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openPDFThroughGDrive(pdfUrl);
                    }
                })
                .show();
    }

    private class PDFReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("num","4");
            String action = intent.getAction();

            if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(getActivity(), "Download Realizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                if (pManager.hasPDFOpener(context)) {
                    String dp = pManager.getLocalPDFPath(downloadedPdfName);
                    openPDF(Uri.parse(dp));
                } else {
                    askToOpenPDFThroughGoogleDrive(pManager.buildPDFLink(downloadedPdfName, downloadedPdfId));
                }

            }
        }
    }


    public void init(View v) {
        mMaterialDiaolg= new MaterialDialog(getActivity());

        Toast.makeText(getActivity(), "Initializes successfully.",
                Toast.LENGTH_SHORT).show();
    }

    public void show(String Title,String Mensagem, String Complemeto, final String tipo) {
        Log.d("aqui","testando");
        if (mMaterialDiaolg != null) {

            mMaterialDiaolg.setTitle(Title).setMessage(Mensagem+ " " + Complemeto + "?")
                    .setPositiveButton("SIM", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDiaolg.dismiss();
                            if(tipo.equals("pdf")) {
                                downloadPdf();
                            }else{
                                consultaOrphanet();
                            }
                           // Toast.makeText(getActivity(), "SIM", Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("NÃO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDiaolg.dismiss();
                   // Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                }
            }).setCanceledOnTouchOutside(false)

                    .setOnDismissListener(

                            new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                   // Toast.makeText(getActivity(), "",
                                           // Toast.LENGTH_SHORT).show();
                                }
                            }).show();

        } else {
            Toast.makeText(getActivity(), "You should init firstly!",
                    Toast.LENGTH_SHORT).show();
        }

        Log.d("aqui","testando2");
    }





}