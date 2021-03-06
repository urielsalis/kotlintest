package com.sksamuel.kotlintest.matchers.types

import io.kotlintest.matchers.types.beInstanceOf
import io.kotlintest.matchers.types.beNull
import io.kotlintest.matchers.types.beOfType
import io.kotlintest.matchers.types.beTheSameInstanceAs
import io.kotlintest.matchers.types.haveAnnotation
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.matchers.types.shouldHaveAnnotation
import io.kotlintest.matchers.types.shouldNotBeInstanceOf
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.matchers.types.shouldNotBeTypeOf
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec
import java.util.*

@Suppress("UnnecessaryVariable")
class TypeMatchersTest : WordSpec() {

  @Retention(AnnotationRetention.RUNTIME)
  annotation class Vod

  @Vod
  class Wibble

  init {

    "typeOf" should {
      "test for exact type" {
        val arrayList: List<Int> = arrayListOf(1, 2, 3)
        arrayList.shouldBeTypeOf<ArrayList<*>>()
        arrayList.shouldNotBeTypeOf<List<*>>()
      }
    }

    "haveAnnotation(annotation)" should {
      "test for the presence of an annotation" {
        Wibble::class.java should haveAnnotation(Vod::class.java)
        Wibble::class.java.shouldHaveAnnotation(Vod::class.java)
      }
    }

    "beInstanceOf" should {
      "test that value is assignable to class" {
        val arrayList: List<Int> = arrayListOf(1, 2, 3)

        arrayList should beInstanceOf(ArrayList::class)
        arrayList.shouldBeInstanceOf<ArrayList<*>>()

        arrayList should beInstanceOf(List::class)

        shouldThrow<AssertionError> {
          arrayList should beInstanceOf(LinkedList::class)
        }

        arrayList.shouldNotBeInstanceOf<LinkedList<*>>()

        shouldThrow<AssertionError> {
          arrayList.shouldNotBeInstanceOf<ArrayList<*>>()
        }
      }
      
      "Allow execution with a lambda" {
        val list = arrayListOf(1, 2, 3)
        
        list.shouldBeInstanceOf<ArrayList<Int>> { it: ArrayList<Int> ->
          it shouldBeSameInstanceAs list
        }
      }

      "accepts null values" {
        val arrayList: List<Int>? = null
        shouldThrow<AssertionError> { arrayList should beInstanceOf(ArrayList::class) }
        shouldThrow<AssertionError> { arrayList.shouldBeInstanceOf<ArrayList<*>>() }
        shouldThrow<AssertionError> { arrayList shouldNot beInstanceOf(List::class) }
        shouldThrow<AssertionError> { arrayList.shouldNotBeInstanceOf<LinkedList<*>>() }
      }
    }

    "beOfType" should {
      "test that value have exactly the same type" {
        val arrayList: List<Int> = arrayListOf(1, 2, 3)

        arrayList should beOfType<ArrayList<Int>>()

        shouldThrow<AssertionError> {
          arrayList should beOfType<LinkedList<Int>>()
        }

        shouldThrow<AssertionError> {
          arrayList should beOfType<List<Int>>()
        }
      }
      
      "Allow execution with a lambda" {
        val list: Any = arrayListOf(1, 2, 3)
        
        list.shouldBeTypeOf<ArrayList<Int>> { it: ArrayList<Int> ->
          it shouldBeSameInstanceAs list
          it[0] shouldBe 1
        }
      }

      "accepts null values" {
        val arrayList: List<Int>? = null
        shouldThrow<AssertionError> { arrayList should beOfType<List<Int>>() }
        shouldThrow<AssertionError> { arrayList.shouldBeTypeOf<List<*>>() }
        shouldThrow<AssertionError> { arrayList shouldNot beOfType<List<Int>>() }
        shouldThrow<AssertionError> { arrayList.shouldNotBeTypeOf<List<*>>() }
      }
    }

    "TypeMatchers.theSameInstanceAs" should {
      "test that references are equal" {
        val b: List<Int>? = listOf(1, 2, 3)
        val a: List<Int>? = b
        val c: List<Int>? = listOf(1, 2, 3)

        a should beTheSameInstanceAs(b)
        a.shouldBeSameInstanceAs(b)

        shouldThrow<AssertionError> {
          a should beTheSameInstanceAs(c)
        }

        shouldThrow<AssertionError> {
          a.shouldBeSameInstanceAs(c)
        }
      }
    }

    "beTheSameInstanceAs" should {
      "test that references are equal" {
        val b = listOf(1, 2, 3)
        val a = b
        val c = listOf(1, 2, 3)

        a should beTheSameInstanceAs(b)
        shouldThrow<AssertionError> {
          a should beTheSameInstanceAs(c)
        }
      }
    }

    "beNull" should {
      val nullString: String? = null
      val nonNullString: String? = "Foo"
      "Pass for a null value" {
        nullString.shouldBeNull()
        nullString should beNull()
      }

      "Fail for a non-null value" {
        shouldThrow<AssertionError> { nonNullString.shouldBeNull() }
        shouldThrow<AssertionError> { nonNullString should beNull() }
      }
    }

    "notBeNull" should {
      val nullString: String? = null
      val nonNullString: String? = "Foo"

      "Pass for a non-null value" {
        nonNullString.shouldNotBeNull()
        nonNullString shouldNot beNull()
      }

      "Fail for a null value" {
        shouldThrow<AssertionError> { nullString.shouldNotBeNull() }
        shouldThrow<AssertionError> { nullString shouldNot beNull() }
      }

      "Allow automatic type cast" {
        fun useString(string: String) {  }

        nonNullString.shouldNotBeNull()
        useString(nonNullString)
        nonNullString shouldBe "Foo"
      }
    }
  }

}
