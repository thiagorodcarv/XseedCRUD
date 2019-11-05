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
                long numeroDeItensComprados = inserirCompras(dao,produtosCarrinho);
                Toast.makeText(getActivity(), numeroDeItensComprados+" itens comprados", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "loja@xseed.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pedido");
                emailIntent.putExtra(Intent.EXTRA_TEXT, conteudo);
                startActivity(Intent.createChooser(emailIntent, "Enviando pedido..."));
                getActivity().finish();
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getActivity().getPackageManager(); //TODO
                try {
                    long numeroDeItensComprados = inserirCompras(dao,produtosCarrinho);
                    Toast.makeText(getActivity(), numeroDeItensComprados+" itens comprados", Toast.LENGTH_SHORT).show();
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    PackageInfo info= pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, conteudo);
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

//    public void sendEmail(View view){
//        long numeroDeItensComprados = inserirCompras(dao,produtosCarrinho);
//            Toast.makeText(getActivity(), numeroDeItensComprados+" itens comprados", Toast.LENGTH_SHORT).show();
//            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                    "mailto", "loja@xseed.com", null));
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pedido");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, conteudo);
//            startActivity(Intent.createChooser(emailIntent, "Enviando pedido..."));
//    }

//    public String gerarPedido(List<Produtos> produtosCarrinho){
//        String conteudo = "Comprado: \n";
//        Integer precoDescontado;
//
//        for(Produtos p : produtosCarrinho){
//            if (p.getQuantidade()!=0) {
//                if ((p.getPrecoDesconto()==null)||p.getPrecoDesconto().equals("")) {
//                    precoDescontado = Integer.valueOf(p.getPreco());
//                } else {
//                    precoDescontado = Integer.valueOf(p.getPreco()) - Integer.valueOf(p.getPrecoDesconto());
//                }
//                conteudo += p.getQuantidade().toString() + ", " + p.getNome() + " por " + precoDescontado + " cada \n";
//            }
//
//        }
//        conteudo += "\t totalizando o valor de: "+ precoTotal.toString();
//        return conteudo;
//    }

}
