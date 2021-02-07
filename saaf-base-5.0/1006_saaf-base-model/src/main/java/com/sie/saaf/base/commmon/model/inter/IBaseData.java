package com.sie.saaf.base.commmon.model.inter;

import java.util.Collection;
import java.util.List;

public interface IBaseData {
    List<String> findVendor(Collection<Integer> respId, Integer userId);

    List<String> findVendorGroup(Collection<Integer> respId, Integer userId);
}
