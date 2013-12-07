package org.vkedco.mobappdev.bindsumlib;

import org.vkedco.mobappdev.bindsumlib.SumRequest;
import org.vkedco.mobappdev.bindsumlib.SumResponse;

interface IBindSumService 
{
	long fibonacciSum(in long n); 	// sum of the first n fibonacci numbers
	long catalanSum(in long n); 	// sum of the first n catalan numbers
	SumResponse sum(in SumRequest req);
}
