package com.example.lojacrud;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lojacrud.BancoPackage.HistoricoDAO;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSendPopUp extends BottomSheetDialogFragment {

    private HistoricoDAO dao;
    private List<Produtos> produtosCarrinho;
    String conteudo;
    Button email;
    Button whatsapp;


    public BottomSendPopUp(HistoricoDAO dao, List<Produtos> produtosCarrinho, String conteudo){
        this.dao = dao;
        this.produtosCarrinho = produtosCarrinho;
        this.conteudo = conteudo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.bottom_send_popup,container,false);
        email = viewRoot.findViewById(R.id.email_buttom);
        whatsapp = viewRoot.findViewById(R.id.zapzap_buttom);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "loja@xseed.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pedido");
                emailIntent.putExtra(Intent.EXTRA_TEXT, conteudo);
                long numeroDeItensComprados = inserirCompras(dao,produtosCarrinho);
                Toast.makeText(getActivity(), numeroDeItensComprados+" itens comprados", Toast.LENGTH_SHORT).show();
                startActivity(Intent.createChooser(emailIntent, "Enviando pedido..."));
                getActivity().finish();
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getActivity().getPackageManager(); //TODO COLOCAR AS MENSAGENS RECEBIDAS NA MAIN ACTIVITY em OnActivityResult
                try {


                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    PackageInfo info= pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");  //TIRANDO O PACKAGE ELE ABRE AS OPÇÕES PARA ENVIO INSTALADAS NO CELULAR
                    waIntent.putExtra(Intent.EXTRA_TEXT, conteudo);
                    long numeroDeItensComprados = inserirCompras(dao,produtosCarrinho);
                    Toast.makeText(getActivity(), numeroDeItensComprados+" itens comprados", Toast.LENGTH_SHORT).show();
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
                getActivity().finish();
            }
        });
        return viewRoot;
    }


    public long inserirCompras(HistoricoDAO dao, List<Produtos> produtos){
        long numeroDeItensComprados = 0;
        for (Produtos p : produtos){
            dao.inserirVenda(p);
            numeroDeItensComprados++;
        }
        return numeroDeItensComprados;
    }


}
