package com.iw.ucafoster.vistas.cajas;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = CajasView.VIEW_NAME)
public class CajasView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "cajas";

    @PostConstruct
    void init() {

        this.addComponent(new Label("cajas"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
