package com.fiveup.core.events.model.response;

import com.fiveup.core.events.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardResp {
    private List<Card> list;
}
