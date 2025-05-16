package hung.deptrai.mycomic.core.data.utils

import hung.deptrai.constants.MdConstants

class MdUtil {
    companion object {
        fun getMangaListOffset(page: Int): Int = (MdConstants.Limits.manga * (page - 1))
    }
}