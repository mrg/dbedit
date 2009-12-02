package com.rubicode.dbedit;

import javax.swing.DefaultComboBoxModel;

/**
 * @author Michael Gentry
 */
public class FetchLimitPulldown extends DefaultComboBoxModel
{
    public FetchLimitPulldown()
    {
        super(new Object[] { "25 Records",
                             "50 Records",
                             "100 Records",
                             "250 Records",
                             "500 Records",
                             "1000 Records",
                             "2500 Records",
                             "5000 Records",
                             "10000 Records",
                             "All Records" });
    }
}
