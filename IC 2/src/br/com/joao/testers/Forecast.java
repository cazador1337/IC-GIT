/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joao.testers;

import Default.Tabela.TableObjectInterface;

/**
 *
 * @author joao
 */
public class Forecast implements TableObjectInterface{

    private double valorReal, valorPrevisto;

    public double getValorReal() {
        return valorReal;
    }

    public void setValorReal(double valorReal) {
        this.valorReal = valorReal;
    }

    public double getValorPrevisto() {
        return valorPrevisto;
    }

    public void setValorPrevisto(double valorPrevisto) {
        this.valorPrevisto = valorPrevisto;
    }

    @Override
    public Object[] atributeToTable() {
        return new Object[]{valorReal, valorPrevisto};
    }

}
