package com.sksamuel.kotlintest.specs.wordspec

import io.kotlintest.matchers.integers.shouldBeGreaterThan
import io.kotlintest.specs.WordSpec

class WordSpecTest : WordSpec() {

  init {
    "a context" should {
      "have a test" {
        2.shouldBeGreaterThan(1)
      }
      "have another test" {
        2.shouldBeGreaterThan(1)
      }
      "have a test with config".config(invocations = 2) {

      }
    }

    "another context" When {

      "using when" Should {
        "have a test" {
          2.shouldBeGreaterThan(1)
        }
        "have a test with config".config(invocations = 2) {
          2.shouldBeGreaterThan(1)
        }
      }

    }
  }
}