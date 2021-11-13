package com.tiago.citiesapi.cities;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    private final CityRepository cityRepository;
    Logger log = LoggerFactory.getLogger(DistanceService.class);

    public DistanceService(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * 2nd option
     *
     * @param city1
     * @param city2
     * @return
     */
    public Double distanceByPointsInMiles(final Long city1, final Long city2) {
        log.info("nativePostgresInMiles({}, {})", city1, city2);
        return cityRepository.distanceByPoints(city1, city2);
    }

    /**
     * 4th option
     *
     * @param city1
     * @param city2
     * @return
     */
    public Double distanceByCubeInMeters(Long city1, Long city2) {
        log.info("distanceByCubeInMeters({}, {})", city1, city2);
        final List<City> cities = cityRepository.findAllById((Arrays.asList(city1, city2)));

        Point p1 = cities.get(0).getLocation();
        Point p2 = cities.get(1).getLocation();

        return cityRepository.distanceByCube(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public List<Long> nearbyCities(Long cityId) throws Exception {
        log.info("cities nearby {city}", cityId);
        Optional<City> optional = cityRepository.findById(cityId);
        if (optional.isPresent()) {
            return cityRepository.nearbyCities(cityId);
        } else {
            throw new Exception("City not found");
        }
    }

}
