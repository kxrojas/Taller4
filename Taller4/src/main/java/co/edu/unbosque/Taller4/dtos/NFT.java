package co.edu.unbosque.Taller4.dtos;

import com.opencsv.bean.CsvBindByName;

public class NFT {

    @CsvBindByName
    private String titulo;

    @CsvBindByName
    private String money;

    @CsvBindByName
    private String path;

    public NFT(){
    }

    public NFT(String titulo, String money, String path) {
        this.titulo = titulo;
        this.money = money;
        this.path = path;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "NFT{" +
                "titulo='" + titulo + '\'' +
                ", money='" + money + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
