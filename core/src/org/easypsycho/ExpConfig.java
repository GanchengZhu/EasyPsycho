/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone
 * to download, examine, use, modify, and distribute, subject to the usual
 * restrictions attached to any GPL software. If you are not familiar with the AGPL,
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/

package org.easypsycho;

import java.util.HashMap;
import java.util.Map;

public class ExpConfig {

    private boolean fullScreen = false;
    private boolean enableEscExit = true;
    private int winHeight = 640;
    private int winWidth = 480;
    private String expName = "org.easypsycho.EasyPsycho";
    private boolean showInfoDialog = true;
    private Map<String, String> expInfo;
    private String blendMode = "avg";
    private int monitorId = 1;
    private boolean showMouse = true;

    public ExpConfig() {
        expInfo = new HashMap<>();
    }

}
