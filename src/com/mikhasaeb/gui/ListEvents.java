/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikhasaeb.gui;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mikhasaeb.entities.categorieevent;
import com.mikhasaeb.entities.event;
import com.mikhasaeb.services.ServiceCategorieEv;
import com.mikhasaeb.services.ServiceEvent;
import com.mikhasaeb.utils.Statics;
import java.util.ArrayList;
import javafx.scene.control.DatePicker;

/**
 *
 * @author ASUS
 */
public class ListEvents extends BaseForm {

    Resources theme = UIManager.initFirstTheme("/theme");
    EncodedImage enc;
    Image imgs;
    ImageViewer imgv;

    public ListEvents(BaseForm previous) {
        setTitle("List Events");
        setLayout(BoxLayout.y());
        ArrayList<event> events = ServiceEvent.getInstance().afficherEv();

        for (event t : events) {
            Container card = new Container(new BorderLayout());
            card.getStyle().setBorder(Border.createLineBorder(1, ColorUtil.GRAY));
            card.getStyle().setMarginUnit(Style.UNIT_TYPE_DIPS);
            card.getStyle().setMargin(Component.BOTTOM, 10);
            card.getStyle().setBgColor(0xFFFFFF);

            Label idLabel = new Label("ID: " + t.getIdEvent());
            Label NomLabel = new Label("Nom event: " + t.getNomEvent());

            idLabel.getStyle().setFgColor(0x000000);
            NomLabel.getStyle().setFgColor(0x000000);

            card.add(BorderLayout.NORTH, idLabel);
            card.add(BorderLayout.CENTER, BoxLayout.encloseY(NomLabel));
            this.add(card);

            //Boutton Delete
            Button btndelete = new Button("delete");
            btndelete.addActionListener(e -> {
                System.out.println("delete 1");
                boolean isConfirmed = Dialog.show("Confirmation", "Are you sure you want to delete this event?", "Yes", "No");
                if (isConfirmed) {
                    System.out.println("delete 2");
                    ServiceEvent.getInstance().suppEvent(t);
                    new ListEvents(this).show(); // refresh the page after deleting the event
                }
            });
            //Boutton MODIFIER
            Button btnUpdate = new Button("Modifier");
//btnUpdate.addActionListener(e -> {
//System.out.println("update 1");
//boolean isConfirmed = Dialog.show("Confirmation", "Are you sure you want to update this event?", "Yes", "No");
//if (isConfirmed) {
//System.out.println("update 2");
//// Create a form to edit the event details
//Form editForm = new Form("Edit Event");
//// Add the necessary input fields to the form
//TextField titleField = new TextField("", t.getNomEvent());
//TextField descriptionField = new TextField("", t.getDescription(), TextArea.MULTILINE);
//DatePicker startDatePicker = new DatePicker();
//startDatePicker.setDate(t.getDateDebut().);
//DatePicker endDatePicker = new DatePicker();
//endDatePicker.setDate(t.getEndDate().toLocalDate());
//TimePicker startTimePicker = new TimePicker();
//startTimePicker.setTime(t.getStartDate().toLocalTime());
//TimePicker endTimePicker = new TimePicker();
//endTimePicker.setTime(t.getEndDate().toLocalTime());
//// Add the input fields to the form
//editForm.add(titleField);
//editForm.add(descriptionField);
//editForm.add(startDatePicker);
//editForm.add(endDatePicker);
//editForm.add(startTimePicker);
//editForm.add(endTimePicker);
//// Add a save button to the form
//Button saveButton = new Button("Save");
//saveButton.addActionListener(saveEvent -> {
//// Update the event with the new values
//Event updatedEvent = new Event(
//t.getId(),
//titleField.getText(),
//descriptionField.getText(),
//LocalDateTime.of(startDatePicker.getDate(), startTimePicker.getTime()),
//LocalDateTime.of(endDatePicker.getDate(), endTimePicker.getTime())
//);
//ServiceEvent.getInstance().modifierEvent(updatedEvent);
//// Show the list of events
//new ListEvents(this).show();
//});
//editForm.add(saveButton);
//// Show the edit form
//editForm.show();
//}
//});
            card.add(BorderLayout.SOUTH, btndelete); // add the button to the container

            card.add(BorderLayout.NORTH, btnUpdate); // add the button to the container.

        }

        //   getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addElement(event t) {
        CheckBox nom = new CheckBox(t.getNomEvent());
        nom.setEnabled(false);
        add(nom);
        CheckBox photo = new CheckBox(t.getPhotoE());
        photo.setEnabled(false);
        add(photo);

//        Image img = t.getPhotoE()
//ImageViewer imageViewer = new ImageViewer(img);
//Container container = new Container();
//container.add(imageViewer);
    }

}
