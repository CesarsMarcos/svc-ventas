package com.svc.ventas.controller;

import com.svc.ventas.service.IChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/charts/")
public class ChartController {

  private final IChartService chartService;

  @GetMapping
  public ResponseEntity<?> getCharts (){
    return ResponseEntity.ok(chartService.getGraficos());
  }

}
