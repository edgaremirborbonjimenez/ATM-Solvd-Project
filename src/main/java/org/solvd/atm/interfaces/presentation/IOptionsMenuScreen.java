package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.interfaces.business.IOptionsMenuBusiness;

public interface IOptionsMenuScreen {
    void showOptionsMenu();
    void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness);
}
