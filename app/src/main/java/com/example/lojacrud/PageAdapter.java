package com.example.lojacrud;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.lojacrud.DestaquesTabPackage.DestaquesTab;
import com.example.lojacrud.HistoricoTabPackage.HistoricoTab;
import com.example.lojacrud.ProdutosTabPackage.ProdutosTab;

import static com.example.lojacrud.MainActivity.fab;

public class PageAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"Destaques", "Produtos", "Histórico"};
    public DestaquesTab destaquesTab = new DestaquesTab();
    public ProdutosTab produtosTab = new ProdutosTab();
    public HistoricoTab historicoTab = new HistoricoTab();

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                return destaquesTab;
            case 1 :
                return produtosTab;
            case 2 :
                return historicoTab;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
