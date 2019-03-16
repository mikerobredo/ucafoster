package com.iw.ucafoster.vistas.security;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import sun.rmi.runtime.Log;

public class LoginScreen extends VerticalLayout {
  
	public LoginScreen(LoginCallback callback) {

        setMargin(true);
        setSpacing(true);

	    setSizeFull();
	    setMargin(false);
	    setSpacing(false);

	    Component loginForm = buildLoginForm(callback);
	    addComponent(loginForm);
	    setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }

    private Component buildLoginForm(LoginCallback callback) {

	    final VerticalLayout loginPanel = new VerticalLayout();
	    loginPanel.setSizeUndefined();
	    loginPanel.setMargin(true);
	    loginPanel.addStyleName(ValoTheme.LAYOUT_CARD);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields(callback));

        return loginPanel;
    }

    private Component buildLabels() {

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.addStyleName("labels");

        Label welcome = new Label("Bienvenido");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("UCA-FOSTER");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);

        labels.setExpandRatio(welcome, 0.4f);
        labels.setExpandRatio(title, 0.6f);

        return labels;
    }

    private Component buildFields(LoginCallback callback) {

        HorizontalLayout fields = new HorizontalLayout();
        fields.addStyleName("fields");

        final TextField username = new TextField("Nombre de usuario");
        username.setIcon(VaadinIcons.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Contraseña");
        password.setIcon(VaadinIcons.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Acceder");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signin.focus();

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(e -> {

            String passwd = password.getValue();
            password.setValue("");
            if(!callback.login(username.getValue(), passwd)) {

                Notification notification = new Notification("Ha ocurrido un error");
                notification.setDescription("<span>Usuario y/o contraseña incorrecto</span>");
                notification.setHtmlContentAllowed(true);
                notification.setStyleName("tray dark small closable login-help");
                notification.setPosition(Position.TOP_CENTER);
                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());
            }
        });
        return fields;
    }

    @FunctionalInterface
    public interface LoginCallback {

        boolean login(String username, String password);
    }
}
