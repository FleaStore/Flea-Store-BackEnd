package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swengineering8.fleastore.domain.Repository.BoothImgFileRepository;
import swengineering8.fleastore.domain.Repository.BoothRepository;
import swengineering8.fleastore.dto.BoothDto;
import swengineering8.fleastore.dto.Response;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class BoothService {

    private final BoothRepository boothRepository;
    private final BoothImgFileRepository boothImgFileRepository;
    private final Response response;

    public ResponseEntity<?> getBoothsOfMarket(Long marketId) {

        return response.success();
    }

    public ResponseEntity<?> getBooth(Long marketId) {

        return response.success();
    }

    public ResponseEntity<?> addBooth(BoothDto boothDto, Long marketId) {

        return response.success();
    }

    public ResponseEntity<?> updateBooth(BoothDto boothDto, Long boothId) {

        return response.success();
    }

    public ResponseEntity<?> deleteBooth(Long boothId) {

        return response.success();
    }

}
