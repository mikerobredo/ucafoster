package com.iw.ucafoster;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.iw.ucafoster.security.*;
import com.iw.ucafoster.vistas.security.*;

@SpringUI
@SpringViewDisplay
@PreserveOnRefresh
@Title("UCA-Foster")
@Theme("dashboard")
@Viewport("user-scalable=no, initial-scale=1.0, shrink-to-fit=no")
public class MainUI extends UI implements ViewDisplay {
	
	@Autowired
	SpringViewProvider viewProvider;
	
	@Autowired
	AuthenticationManager authenticationManager;

    MenuLayout root = new MenuLayout();
    ComponentContainer viewDisplay = root.getContentContainer();

    CssLayout menu = new CssLayout();
    CssLayout menuItemsLayout = new CssLayout();

    private LinkedHashMap<String, String> menuItems = new LinkedHashMap<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setLocale(Locale.US);
        viewDisplay.addStyleName("view-content");

        Responsive.makeResponsive(this);

        addStyleName(ValoTheme.UI_WITH_MENU);
    	
    	this.getUI().getNavigator().setErrorView(ErrorView.class);
    	viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
    	
    	if (SecurityUtils.isLoggedIn()) {
    		pantallaPrincipal();
    		removeStyleName("loginview");
		} else {
			showLoginScreen();
			addStyleName("loginview");
		}
    }
    
    private void showLoginScreen() {
    	setContent(new LoginScreen(this::login));
    }
    
    private boolean login(String username, String password) {
		try {
			Authentication token = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			// Reinitialize the session to protect against session fixation
			// attacks. This does not work with websocket communication.
			VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
			SecurityContextHolder.getContext().setAuthentication(token);
			
			pantallaPrincipal();
			return true;
		} catch (AuthenticationException ex) {
			return false;
		}
	}
    
    private void pantallaPrincipal() {

        setContent(root);

        root.setWidth("100%");

        root.addMenu(buildMenu());

        if(SecurityUtils.hasRole("ROL-EMPLEADO") || SecurityUtils.hasRole("ROL-ENCARGADO"))
            getUI().getNavigator().navigateTo("pedidos");
        else
            getUI().getNavigator().navigateTo("establecimientos");
        menuItemsLayout.getComponent(1).addStyleName("selected");

        getUI().getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                for (Iterator<Component> it = menuItemsLayout.iterator(); it
                        .hasNext();) {
                    it.next().removeStyleName("selected");
                }
                for (Map.Entry<String, String> item : menuItems.entrySet()) {
                    if (event.getViewName().equals(item.getKey())) {
                        for (Iterator<Component> it = menuItemsLayout
                                .iterator(); it.hasNext();) {
                            Component c = it.next();
                            if (c.getCaption() != null && c.getCaption()
                                    .startsWith(item.getValue())) {
                                c.addStyleName("selected");
                                break;
                            }
                        }
                        break;
                    }
                }
                menu.removeStyleName("valo-menu-visible");
            }
        });
    }

    private CssLayout buildMenu() {

        if(SecurityUtils.hasRole("ROL-EMPLEADO")) {
            menuItems.put("pedidos", "Pedidos");
            menuItems.put("clientes", "Clientes");
            menuItems.put("mesas", "Mesas");
        }

        if(SecurityUtils.hasRole("ROL-ENCARGADO")) {
            menuItems.put("pedidos", "Pedidos");
            menuItems.put("clientes", "Clientes");
            menuItems.put("mesas", "Mesas");
            menuItems.put("cajas", "Cajas");
            menuItems.put("zonas", "Zonas local");
        }

        if(SecurityUtils.hasRole("ROL-GERENTE")) {
            menuItems.put("establecimientos", "Establecimientos");
            menuItems.put("empleados", "Empleados");
            menuItems.put("familias", "Familias de productos");
            menuItems.put("productos", "Productos");
            menuItems.put("ingredientes", "Ingredientes");
        }

        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");
        menu.addComponent(top);

        Button showMenu = new Button("Menu", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                if (menu.getStyleName().contains("valo-menu-visible"))
                    menu.removeStyleName("valo-menu-visible");
                else
                    menu.addStyleName("valo-menu-visible");
            }
        });

        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName("valo-menu-toggle");
        showMenu.setIcon(VaadinIcons.LIST);
        menu.addComponent(showMenu);

        Label title = new Label("<h3><strong>UCA-Foster</strong></h3>", ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        menu.addComponent(menuItemsLayout);

        Label label = null;

        for(Map.Entry<String, String> item : menuItems.entrySet()) {
            if(item.getKey().equals("pedidos")) {
                label = new Label("Empleados", ContentMode.HTML);
                label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
                label.addStyleName(ValoTheme.LABEL_H4);
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            if(item.getKey().equals("cajas")) {
                label = new Label("Encargado", ContentMode.HTML);
                label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
                label.addStyleName(ValoTheme.LABEL_H4);
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            if(item.getKey().equals("establecimientos")) {
                label = new Label("Gerente", ContentMode.HTML);
                label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
                label.addStyleName(ValoTheme.LABEL_H4);
                label.setSizeUndefined();
                menuItemsLayout.addComponent(label);
            }
            Button b = new Button(item.getValue(), new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {

                    getUI().getNavigator().navigateTo(item.getKey());
                }
            });

            b.setCaptionAsHtml(true);
            b.setPrimaryStyleName(ValoTheme.MENU_ITEM);

            switch(item.getKey()) {
                case "pedidos": b.setIcon(VaadinIcons.REPLY); break;
                case "clientes": b.setIcon(VaadinIcons.CASH); break;
                case "cajas": b.setIcon(VaadinIcons.INBOX); break;
                case "zonas": b.setIcon(VaadinIcons.AREA_SELECT); break;
                case "mesas": b.setIcon(VaadinIcons.MAP_MARKER); break;
                case "establecimientos": b.setIcon(VaadinIcons.HOME); break;
                case "empleados": b.setIcon(VaadinIcons.USERS); break;
                case "familias": b.setIcon(VaadinIcons.TABLE); break;
                case "productos": b.setIcon(VaadinIcons.CUTLERY); break;
                case "ingredientes": b.setIcon(VaadinIcons.CUBES); break;
            }

            menuItemsLayout.addComponent(b);
        }

        label = new Label("Sesión", ContentMode.HTML);
        label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
        label.addStyleName(ValoTheme.LABEL_H4);
        label.setSizeUndefined();
        menuItemsLayout.addComponent(label);

        Button logoutButton = new Button("Logout", event -> logout());
        logoutButton.setCaptionAsHtml(true);
        logoutButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        logoutButton.setIcon(VaadinIcons.EXIT);
        menuItemsLayout.addComponent(logoutButton);

        return menu;
    }

    @Override
    public void showView(View view) {

        viewDisplay.removeAllComponents();
        viewDisplay.addComponent((Component) view);
    }
    
    private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
}