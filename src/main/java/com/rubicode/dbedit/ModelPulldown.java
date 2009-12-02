package com.rubicode.dbedit;

import javax.swing.DefaultComboBoxModel;

/**
 * @author Michael Gentry
 */
public class ModelPulldown extends DefaultComboBoxModel
{
    public ModelPulldown()
    {
        super(new Object[] { "Please open a model...", "Foo", "Bar" });
    }
}
