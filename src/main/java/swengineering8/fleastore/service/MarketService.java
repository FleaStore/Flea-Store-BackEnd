package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swengineering8.fleastore.dto.MarketDto;
import swengineering8.fleastore.dto.Response;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class MarketService {

    private final Response response;

    public ResponseEntity<?> getMarkets(int index) {

        return response.success();
    }

    public ResponseEntity<?> getDetailInfo(Long marketId) {

        return response.success();
    }

    public ResponseEntity<?> addMarket(MarketDto marketDto, Long memberId) {

        return response.success();
    }

    public ResponseEntity<?> updateMarket(MarketDto marketDto, Long marketId) {

        return response.success();
    }

    public ResponseEntity<?> deleteMarket(Long marketId) {

        return response.success();
    }

}
