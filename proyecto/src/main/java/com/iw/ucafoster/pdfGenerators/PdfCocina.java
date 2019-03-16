package com.iw.ucafoster.pdfGenerators;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.iw.ucafoster.entidades.Ingrediente;
import com.iw.ucafoster.entidades.LineaPedido;
import com.iw.ucafoster.entidades.Pedido;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PdfCocina {

    private Pedido pedido, before, toPrint;

    public PdfCocina(Pedido pedido, Pedido before) {

        this.pedido = pedido;
        this.before = before;
    }

    public void convertToPdf() throws IOException {

        toPrint = pedido;
        if(before != null) {
            if(pedido.getLineasPedido().size() != before.getLineasPedido().size())
                toPrint.setLineasPedido(nuevasLineas(before, pedido));
        }

        String path = "../Tickets/Cocina/Pedido_" + pedido.getId() + "(" + pedido.getFecha() + ").pdf";

        File file = new File(path);
        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        document.add(new Paragraph("Pedido " + toPrint.getId()).setFont(font).setFontSize(20.0f).setUnderline());
        document.add(new Paragraph());
        document.add(new Paragraph("Fecha: " + toPrint.getFecha()));

        Iterator<LineaPedido> iteratorLineaPedido = toPrint.getLineasPedido().iterator();

        while(iteratorLineaPedido.hasNext()) {

            LineaPedido lp = iteratorLineaPedido.next();
            document.add(new Paragraph(lp.getProducto().getNombre()).setFont(font).setFontSize(15f));
            document.add(new Paragraph("Cantidad: " + lp.getCantidad()));
            Iterator<Ingrediente> iteratorIngrediente = lp.getProducto().getIngredientes().iterator();

            if(iteratorIngrediente.hasNext()) {

                String ingredientes = "Ingredientes: ";
                while(iteratorIngrediente.hasNext()) {

                    Ingrediente ingActual = iteratorIngrediente.next();
                    ingredientes += ingActual.getNombre() + ", ";
                }
                document.add(new Paragraph(ingredientes));
            }
            document.add(new Paragraph(""));
        }
        document.close();
    }

    private List<LineaPedido> nuevasLineas(Pedido pedido_before, Pedido pedido_after) {

        List<LineaPedido> result = new ArrayList<>();

        int first_index = pedido_after.getLineasPedido().size() - pedido_before.getLineasPedido().size() + 1;
        for(int i = first_index ; i < pedido_after.getLineasPedido().size() ; i ++)
            result.add(pedido_after.getLineasPedido().get(i));

        return result;
    }
}
