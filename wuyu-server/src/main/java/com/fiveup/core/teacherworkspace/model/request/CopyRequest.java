package com.fiveup.core.teacherworkspace.model.request;

import com.fiveup.core.teacherworkspace.model.Lesson;
import lombok.Data;
import lombok.NonNull;

@Data
@NonNull
public class CopyRequest {
    private Lesson source;
    private Lesson target;
    private boolean overwrite;
}
