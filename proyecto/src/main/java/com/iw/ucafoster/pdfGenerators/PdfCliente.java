package com.iw.ucafoster.pdfGenerators;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.iw.ucafoster.entidades.LineaPedido;
import com.iw.ucafoster.entidades.Pedido;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PdfCliente {

    private Pedido pedido;
    private DecimalFormat df;

    public PdfCliente(Pedido pedido) {

        this.pedido = pedido;
    }

    public void convertToPdf() throws IOException {

        String path = "../Tickets/Clientes/Pedido_" + pedido.getId() + "(" + pedido.getFecha() + ").pdf";

        df = new DecimalFormat("####0.00 €");

        List<LineaPedido> listSumarized = sumarizar(pedido.getLineasPedido());

        File file = new File(path);
        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(path);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);

        document.add(new Paragraph("UCA-FOSTER")
                .setFont(font)
                .setBold()
                .setFontSize(30.0f)
        );

        document.add(new Paragraph());
        document.add(new Paragraph("Código pedido: " + pedido.getId()).setFont(font).setFontSize(8.0f));
        document.add(new Paragraph("Fecha: " + pedido.getFecha()).setFont(font).setFontSize(8.0f));
        document.add(new Paragraph());
        document.add(new Paragraph("Factura").setFont(font).setFontSize(20.0f).setBold());
        document.add(new Paragraph());

        Table table = new Table(4);

        table.setBorderBottom(new SolidBorder(1));

        Cell cell = null;

        cell = new Cell()
                .add("Cantidad")
                .setPadding(10)
                .setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER);
        table.addCell(cell);

        cell = new Cell()
                .add("Producto")
                .setPadding(10)
                .setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER);
        table.addCell(cell);

        cell = new Cell()
                .add("Precio unidad")
                .setPadding(10)
                .setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER);
        table.addCell(cell);

        cell = new Cell()
                .add("Total")
                .setPadding(10)
                .setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER);
        table.addCell(cell);

        Iterator<LineaPedido> iterator = listSumarized.iterator();

        while(iterator.hasNext()) {

            LineaPedido lp = iterator.next();
            cell = new Cell()
                    .add(lp.getCantidad().toString())
                    .setPadding(10)
                    .setBorder(Border.NO_BORDER);
            table.addCell(cell);

            cell = new Cell()
                    .add(lp.getProducto().getNombre())
                    .setPadding(10)
                    .setBorder(Border.NO_BORDER);
            table.addCell(cell);

            cell = new Cell()
                    .add(String.valueOf(df.format(lp.getProducto().getPrecio() * (1 + (lp.getProducto().getFamilia().getIva() / 100)))))
                    .setPadding(10)
                    .setBorder(Border.NO_BORDER);
            table.addCell(cell);

            cell = new Cell()
                    .add(df.format((lp.getProducto().getPrecio() * lp.getCantidad()) * (1 + (lp.getProducto().getFamilia().getIva() / 100))))
                    .setPadding(10)
                    .setBorder(Border.NO_BORDER);
            table.addCell(cell);
        }

        document.add(table);

        document.add(new Paragraph());
        document.add(new Paragraph("Total a pagar: " + df.format(pedido.getTotal()))
                .setFont(font)
                .setFontSize(10.0f));

        document.close();
    }

    private List<LineaPedido> sumarizar(List<LineaPedido> lineas) {

        List<LineaPedido> result = new ArrayList<>();

        for(int i = 0 ; i < lineas.size() ; i ++) {
            if(inArray(result, lineas.get(i))) {
                LineaPedido lp = findLP(result, lineas.get(i).getProducto().getId());
                lp.setCantidad(lp.getCantidad() + lineas.get(i).getCantidad());
            } else {
                result.add(lineas.get(i));
            }
        }

        return result;
    }

    private boolean inArray(List<LineaPedido> res_list, LineaPedido act) {
        boolean in = false;
        for(int i = 0 ; i < res_list.size() && !in ; i ++) {
            if(res_list.get(i).getProducto().getId() == act.getProducto().getId())
                in = true;
        }
        return in;
    }

    private LineaPedido findLP(List<LineaPedido> res_list, Integer id) {
        LineaPedido lp = null;

        for(int i = 0 ; i < res_list.size() && lp == null ; i ++) {
            if(res_list.get(i).getProducto().getId() == id)
                lp = res_list.get(i);
        }
        return lp;
    }
}
