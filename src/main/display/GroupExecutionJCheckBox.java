package main.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public abstract class GroupExecutionJCheckBox extends JCheckBox{

	private static final long serialVersionUID = 8566661393155553928L;
	static ArrayList<GroupExecutionJCheckBox> allInstances = new ArrayList<>();
	GroupExecutionJCheckBox selfRef;
	public GroupExecutionJCheckBox() {
		super();
		selfRef = this;
		allInstances.add(this);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selfRef.performBeforeGroupUpdate();
				for(GroupExecutionJCheckBox checkbox: allInstances) {
					if(checkbox.isSelected()) {
						checkbox.performUpdate();
					}
				}
				selfRef.performAfterGroupUpdate();
			}
			
		});
	}

	public abstract void performBeforeGroupUpdate();
	public abstract void performUpdate();
	public abstract void performAfterGroupUpdate();
}
