/**
 * Created by joeyz on 7/6/17.
 */


//Example #1,  not Closure
function sayHello(name){
    var text = 'Hello ' + name;
    var say = function(){
        console.log(text);
    }  // define the function, and assign the function reference to "say"

    say(); //
}

sayHello('Joe'); //log "Hello Joe"


//Example #2, Closure
//return a reference to a function
function sayHello2(name){
    var text = 'Hello ' + name; // local variable
    var say = function() {
        console.log(text);
    }

    return say;
}

var say2 = sayHello2('Bob'); //
say2(); //log "Hello Bob", say2 and say both are the reference of an anonymous function


//Example #3,
//the local variable are not copied, they are kept by reference. It is kind of like keeping a stack-frame in memroy when the outer funciton
function say667(){
    //local variable that ends up within closure
    var num = 42;
    var say = function(){
        console.log(num);
    }
    num++;
    return say;
}

var sayNumber = say667();
sayNumber(); // log 43
sayNumber(); // log 43


//Example #4, All three global functions have a common reference to the same closure because they are all declared within a single call to setupSomeGlobals()
var gLogNumber, gIncreaseNumber, gSetNumber;
function setupSomeGlobals() {
    //local variable that ends up within closure
    var num = 42;

    //store some reference to function as global variables
    gLogNumber = function() {
        console.log(num);
    }

    gIncreaseNumber = function () {
        num++;
    }

    gSetNumber = function (x) {
        num = x;
    }
}

setupSomeGlobals(); //init one "stack-frame"
gIncreaseNumber();
gLogNumber(); //43
gSetNumber(5);
gLogNumber(); //5

var oldLog = gLogNumber;

setupSomeGlobals(); //init the other "stack-frame"
gLogNumber(); //42

oldLog(); // 5
/*
  Note that in the above example, if you call setupSomeGlobals() again, then a new closure (stack-frame!) is created.
  The old gLogNumber, gIncreaseNumber, gSetNumber variables are overwritten with new functions that have the new closure.
  (In JavaScript, whenever you declare a function inside another function, the inside function(s) is/are recreated again each time the outside function is called.)
 */


//Example #5,
//Be very careful if you are defining a function within a loop; the local variable from the closure do not act as you 
function buildList(list) {
    var result = [];
    for(var i = 0; i < list.length; i++){
        var item = 'item' + i;
        result.push(function () {
            console.log(item + ' ' + list[i])
        });
    }
    
    return result;
}

function testList() {
    var fnList = buildList([1, 2, 3]);

    //using j only to help prevent confusion -- could use i.
    for (var j = 0; j < fnList.length; j++) {
        fnList[j]();
    }
}

testList(); //logs "item2 undefined" 3 times

/*
 The line
     result.push( function() {console.log(item + ' ' + list[i])}
 adds a reference to an anonymous function three times to the result array.

 If you are not so familiar with anonymous functions think of it like:
   pointer = function() {console.log(item + ' ' + list[i])};
   result.push(pointer);

 Note that when you run the example, "item2 undefined" is alerted three times!
 This is because just like previous examples, there is only one closure for the local variables for buildList.
 When the anonymous functions are called on the line fnlist[j](); they all use the same single closure,
 and they use the current value for i and item within that one closure (where i has a value of 3 because the loop had completed, and item has a value of 'item2'). Note we are indexing from 0 hence item has a value of item2. And the i++ will increment i to the value 3.
 */


//Example #6
/*
    The closure contains any local variables that were declared inside the outer funciton before it exited.
 */
function sayAlice() {
    var say = function () {
        console.log(alice);
    }

    //local variable that ends up within clo
    var alice = 'Hello Alice';
    return say;
}

sayAlice()(); //logs "Hello Alice"


//Example #7
/*
   each call creates a separate closure for the local variable. 
   There is not a single closure per function declaration. There is a closure for each call to a function
 */
function newClosure(someNum, someRef){
    //local variable that end up within closure
    var num = someNum;
    var anArray = [1,2,3];
    var ref = someRef;

    return function (x) {
        num += x;
        anArray.push(num);
        console.log('num: ' + num +
        '; anArray: ' + anArray.toLocaleString() +
        '; ref.someVar: ' + ref.someVar + ';');
    }

    obj = {someVar: 4};
    fn1 = newClosure(4, obj);
    fn2 = newClosure(5, obj);

    fn1(1); //num: 5; anArray: 1,2,3,5; ref.someVar: 4;
    fn2(1); //num: 6; anArray: 1,2,3,6; ref.someVar: 4;
    
    obj.someVar++;

    fn1(2); //num: 7; anArray: 1,2,3,5,7; ref.someVar: 5;
    fn2(2); //num: 8; anArray: 1,2,3,6,8; ref.someVar: 5;
}