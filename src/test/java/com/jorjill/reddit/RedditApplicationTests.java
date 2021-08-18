package com.jorjill.reddit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RedditApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddTwoNumbers(){
		int numOne = 1;
		int numTwo = 2;
		int result = underTest.add(numOne,numTwo);
		int expected = 3;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator{
		int add(int a,int b){return a+b;}
	}

}
