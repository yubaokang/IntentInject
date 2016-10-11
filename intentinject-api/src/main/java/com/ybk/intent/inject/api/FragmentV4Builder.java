/**
 * Copyright (C) 2010-2016 eBusiness Information, Excilys Group
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ybk.intent.inject.api;

import android.support.v4.app.Fragment;

public abstract class FragmentV4Builder extends BaseBundleBuilder<Fragment> {

    public FragmentV4Builder(Fragment fragment) {
        super(fragment);
    }

    public Fragment build() {
        i.setArguments(bundle);
        return i;
    }
}
