package com.iw.ucafoster.vistas.productos;


import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.iw.ucafoster.entidades.FamiliaProducto;
import com.iw.ucafoster.entidades.Ingrediente;
import com.iw.ucafoster.entidades.Producto;
import com.iw.ucafoster.services.FamiliaProductoService;
import com.iw.ucafoster.services.IngredienteService;
import com.iw.ucafoster.services.ProductoService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class ProductoEditor extends VerticalLayout{
	
	private static final long serialVersionUID = 1L;

	private final ProductoService service;
	private final IngredienteService ingService;
	private final FamiliaProductoService famService;

    /* Current producto */
    private Producto producto;

    private Binder<Producto> binder = new Binder<>(Producto.class);

    /* Text fields */
    TextField nombre = new TextField("Nombre");
    TextField descripcion = new TextField("Descripcion");
    TextField precio = new TextField("Precio");

    /* Error labels */
    Label nombreStatus = new Label();
    Label descripcionStatus = new Label();
    Label precioStatus = new Label();
    Label ingredientesStatus = new Label();
    Label familiaStatus = new Label();

    /* Action buttons */
    Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Eliminar", VaadinIcons.TRASH);
    
    /* Selectores */
    TwinColSelect<Ingrediente> selectorIng = new TwinColSelect<>("Selección de los ingredientes del producto");
    ComboBox<FamiliaProducto> selectorFam = new ComboBox<>();

    /* Layout for buttons */
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public ProductoEditor(ProductoService service, IngredienteService ingService, FamiliaProductoService famService) {

        Notification notification = new Notification("Ha ocurrido un error");
        notification.setDescription("<span>Algunos campos no cumplen los requisitos de validación</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.setDelayMsec(5000);

        String errorRequired = "Campo requerido";

        nombreStatus.addStyleName("validation-error");
        descripcionStatus.addStyleName("validation-error");
        precioStatus.addStyleName("validation-error");
        ingredientesStatus.addStyleName("validation-error");
        familiaStatus.addStyleName("validation-error");

        this.service = service;
        this.ingService = ingService;
        this.famService = famService;

        addComponents(nombreStatus, nombre, descripcionStatus, descripcion, precioStatus, precio,
                ingredientesStatus, selectorIng, familiaStatus, selectorFam, actions);

        nombre.setWidth("100%");
        descripcion.setWidth("100%");
        precio.setWidth("100%");
        
        selectorIng.setItems(ingService.findAll());
        selectorIng.setLeftColumnCaption("Ingredientes disponibles");
        selectorIng.setRightColumnCaption("Ingredientes del producto");
        selectorIng.setWidth("100%");
        selectorIng.setRequiredIndicatorVisible(true);

        selectorFam.setWidth("100%");
        selectorFam.setItems(famService.findAll());
        selectorFam.setCaption("Familia del producto");
        selectorFam.setPlaceholder("Selecciona una familia");
        selectorFam.setItemCaptionGenerator(FamiliaProducto::getNombre);
        selectorFam.setEmptySelectionAllowed(false);
        selectorFam.setRequiredIndicatorVisible(true);
        
        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(Producto::getNombre, Producto::setNombre);

        binder.forField(descripcion)
                .asRequired(errorRequired)
                .withStatusLabel(descripcionStatus)
                .bind(Producto::getDescripcion, Producto::setDescripcion);

        binder.forField(precio)
                .asRequired(errorRequired)
                .withValidator(str -> str != null && Float.parseFloat(str) > 0.0f, errorRequired)
                .withConverter(new StringToFloatConverter("Introduzca un número"))
                .withStatusLabel(precioStatus)
                .bind(Producto::getPrecio, Producto::setPrecio);

        binder.forField(selectorIng)
                .withValidator(list -> list.size() > 0, errorRequired)
                .withStatusLabel(ingredientesStatus)
                .bind(Producto::getIngredientes, Producto::setIngredientes);

        binder.forField(selectorFam)
                .asRequired(errorRequired)
                .withStatusLabel(familiaStatus)
                .bind(Producto::getFamilia, Producto::setFamilia);

        setSpacing(true);
        setMargin(false);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk()) {
                service.save(producto);
            } else
                notification.show(Page.getCurrent());
        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> {
            service.delete(producto);
            selectorFam.setValue(null);
        });

        cancel.addClickListener(e -> editProducto(producto));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editProducto(Producto prod) {
    	
    	refrescar();
    	
        if(prod == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = prod.getId() != null;
        if(persisted)
            producto = service.findOne(prod.getId());
        else
            producto = prod;
        cancel.setVisible(persisted);
        binder.setBean(producto);
        setVisible(true);
        save.focus();
        nombre.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {

        save.addClickListener(e -> {
            if(binder.validate().isOk()) {
            	selectorFam.setValue(null);
                h.onChange();
            }
        });
        delete.addClickListener(e -> h.onChange());
    }
    
    public void refrescar(){
    	selectorIng.setItems(ingService.findAll());
    	selectorFam.setItems(famService.findAll());
    }
}
