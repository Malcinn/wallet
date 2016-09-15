package pl.lodz.uni.math.app.controller.mainwindow;

import javafx.scene.control.Label;

public class LabelInfoController {

	private Label labelInfo;

	public LabelInfoController(Label labelInfo) {
		super();
		this.labelInfo = labelInfo;
	}

	public void update(String text) {
		labelInfo.setText(text);
	}

}
