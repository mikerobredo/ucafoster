package com.iw.ucafoster;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MenuLayout extends HorizontalLayout {

    CssLayout contentArea = new CssLayout();
    CssLayout menuArea = new CssLayout();

    public MenuLayout() {

        setSizeFull();
        setSpacing(false);

        menuArea.setPrimaryStyleName("valo-menu");
        menuArea.setId("dashboard-menu");

        contentArea.addStyleName("mainview");
        contentArea.addStyleName("v-scrollable");
        contentArea.setSizeFull();

        addComponents(menuArea, contentArea);
        setExpandRatio(contentArea, 1);
    }

    public ComponentContainer getContentContainer() {

        return contentArea;
    }

    public void addMenu(Component menu) {

        menu.addStyleName(ValoTheme.MENU_PART);
        menu.addStyleName("sidebar");
        menu.addStyleName("no-vertical-drag-hints");
        menu.addStyleName("no-horizontal-drag-hints");
        menuArea.addComponent(menu);
    }
}
