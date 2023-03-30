package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.mapper.StatsMapper;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStats;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = statsRepository.save(StatsMapper.toEndpointHit(endpointHitDto));
        log.info("Hit created with id {}", endpointHit.getId());
        return StatsMapper.toEndpointHitDto(endpointHit);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start,
                                       LocalDateTime end,
                                       List<String> uris,
                                       Boolean unique) {
        log.info("Stats sent");
        Optional<ViewStats> viewStatsForAll = statsRepository.getStatsAll(start, end);
        long hits;
        if (viewStatsForAll.isPresent()) {
            hits = viewStatsForAll.get().getHits();
        } else {
            hits = 0;
        }
        List<ViewStatsDto> viewStatsDtos;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                viewStatsDtos = statsRepository.getStatsWithoutUriUnique(start, end).stream()
                        .map(StatsMapper::toViewStatsDto)
                        .collect(Collectors.toList());
                return viewStatsDtos;
            } else {
                viewStatsDtos = statsRepository.getStatsWithoutUriNotUnique(start, end).stream()
                        .map(StatsMapper::toViewStatsDto)
                        .collect(Collectors.toList());
                return viewStatsDtos;
            }
        }
        if (unique) {
            viewStatsDtos = statsRepository.getStatsUnique(start, end, uris).stream()
                    .map(StatsMapper::toViewStatsDto)
                    .collect(Collectors.toList());
            if (uris.size() == 1 && uris.get(0).equalsIgnoreCase("/events")) {
                viewStatsDtos.forEach(view -> view.setHits(view.getHits() + hits));
            }
            return viewStatsDtos;
        } else {
            viewStatsDtos = statsRepository.getStatsNotUnique(start, end, uris).stream()
                    .map(StatsMapper::toViewStatsDto)
                    .collect(Collectors.toList());
            if (uris.size() == 1 && uris.get(0).equalsIgnoreCase("/events")) {
                viewStatsDtos.forEach(view -> view.setHits(view.getHits() + hits));
            }
            return viewStatsDtos;
        }
    }
}
