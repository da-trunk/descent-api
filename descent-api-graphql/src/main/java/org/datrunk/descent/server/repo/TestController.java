package org.datrunk.descent.server.repo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// This only works with a war deploy
@RestController
public class TestController {
  @GetMapping("/test/controller")
  public String handler() {
    return "success!";
  }
}
