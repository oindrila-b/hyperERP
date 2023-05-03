package com.example.hypererp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class InvoiceForm {

    @FXML
    TextField txtitemNameFld = new TextField();

    @FXML
    TextField txtquantityFld = new TextField();

    @FXML
    TextField txtitempriceFld = new TextField();

    @FXML
    TextField txtsubTotalFld = new TextField();

    @FXML
    TextField txttotalAmountFld = new TextField();

    @FXML
    TextField txtpaidAmtFld = new TextField();

    @FXML
    TextField txtbalanceFld = new TextField();


    Double totalAmount = 0.0;
    Double cash = 0.0;
    Double balance = 0.0;
    Double bHeight = 0.0;

    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> itemPrice = new ArrayList<>();
    ArrayList<String> subtotal = new ArrayList<>();


    @FXML
    protected void onAddButton(ActionEvent event) {
        System.out.println("Clicked add button");
        // print the input values
        System.out.println(txtitemNameFld.getText());
        System.out.println(txtquantityFld.getText());
        System.out.println(txtitempriceFld.getText());
        System.out.println(txtsubTotalFld.getText());

        //add the input values in the list
        itemName.add(txtitemNameFld.getText());
        quantity.add(txtquantityFld.getText());
        itemPrice.add(txtitempriceFld.getText());
        subtotal.add(txtsubTotalFld.getText());

        // print out the list of inputs
        itemName.forEach(e -> {
            System.out.println(e);
        });

        // clear the current text fields
        clear();

        // calculating the total amount
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        subtotal.forEach(e -> {
            double total = Double.parseDouble(e);
            totalAmount += total;
        });
        subtotal.clear();
        System.out.println("Total Amount = " + totalAmount);
        txttotalAmountFld.setText(totalAmount.toString());

    }

    private void clear() {
        txtitemNameFld.setText("");
        txtquantityFld.setText("");
        txtitempriceFld.setText("");
        txtsubTotalFld.setText("");
    }

    @FXML
    protected void onPrintAction(ActionEvent event) {
        System.out.println("Clicked print button");

        bHeight = Double.valueOf(itemName.size());
        //JOptionPane.showMessageDialog(rootPane, bHeight);

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(), getPageFormat(pj));
        try {
            pj.print();

        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(8);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }


    public class BillPrintable implements Printable {


        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {

            int r = itemName.size();
            ImageIcon icon = new ImageIcon("/home/ohh/Hyperslips/hypERP/asset/hypererp.png");
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());


                try {
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    // int headerRectHeighta=40;


                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawImage(icon.getImage(), 50, 20, 90, 30, null);
                    y += yShift + 30;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += yShift;
                    g2d.drawString("         HyperERP        ", 12, y);
                    y += yShift;
                    g2d.drawString("   Invoice Bill Gnerated ", 12, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += headerRectHeight;

                    g2d.drawString(" Item Name                  Price   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += headerRectHeight;

                    for (int s = 0; s < r; s++) {
                        g2d.drawString(" " + itemName.get(s) + "                            ", 10, y);
                        y += yShift;
                        g2d.drawString("      " + quantity.get(s) + " * " + itemPrice.get(s), 10, y);
                        g2d.drawString(subtotal.get(s), 160, y);
                        y += yShift;

                    }

                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Total amount:               " + txttotalAmountFld.getText() + "   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Cash      :                 " + txtpaidAmtFld.getText() + "   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Balance   :                 " + txtbalanceFld.getText() + "   ", 10, y);
                    y += yShift;

                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       THANK YOU!            ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }
}
