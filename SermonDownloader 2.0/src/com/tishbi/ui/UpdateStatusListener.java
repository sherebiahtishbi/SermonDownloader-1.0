package com.tishbi.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.tishbi.sitecontent.SermonIndexCache;

public class UpdateStatusListener implements PropertyChangeListener
{
	private JLabel lblStatus;
	
	public UpdateStatusListener(JLabel lbl)
	{
		this.lblStatus = lbl;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e)
	{
		if (e.getPropertyName() == "updateprogress")
		{
			if (e.getNewValue() != null)
			{
				lblStatus.setText(e.getNewValue().toString());
				lblStatus.setIcon(new ImageIcon(SermonIndexCache.getSyncupImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH)));
			}
			else
			{
				lblStatus.setText("");
				lblStatus.setVerifyInputWhenFocusTarget(false);
			}
		}
	}

}
