package com.rubicode.dbedit;

import javax.swing.DefaultComboBoxModel;

/**
 * @author Michael Gentry
 */
public class EntityPulldown extends DefaultComboBoxModel
{
    public EntityPulldown()
    {
        super(new Object[] { "Please open a model..." });
    }
}
