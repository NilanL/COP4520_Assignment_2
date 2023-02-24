# COP4520 Assignment 2

## Part 1

### Methodology
Figuring out a proper solution to the cupcake problem was difficult, however I had eventually centered my thought around one core idea: If the guests cannot communicate verbally, and can presumably not see one another, than what variable can be changed to create another form of commmunication? This variable was the cupcake. The cupcake is either present or not present; a binary outcome. Therefore, the cupcake could be utilized as a determiner on the binary outcome of whether or not a guest has completed the labyrinth. If the cupcake is present, then no guest has visited. If is not present, then a guest has visited. However, there are an indeterminate number of guests? Possibly dozens or even hundreds! How can a binary value account for that many people? I then thought to reduce the concept of dozens of guests, into just two types of guests. Either the guest looks for a present cupcake, or the guest looks for an absent cupcake. After this point the rest began to become very clear, as there would then need to be a form of counter to determine how many times the state of the cupcake has switched. Some integer N number of cupcake's eaten could represent N number of guests having visited the end of the labyrinth. And who would account for the eaten cupcakes? A designated counter guest! As the guests are allowed to meet beforehand, they can assign a leader guest who would be responsible for counting the amount of times he/she has found an eaten cupcake. Guests would continuely enter the labyrinth until the leader guest has accounted for N - 1 guests, with himself/herself being accounted as the final guest.
</br>
### Experimental Evaulation
Multiple different implementations were attempted, however I converged on a model of a while loop which will repeat until the flag "allGuestsHaveGone" is set to true. This flag is only set by the "leader guest" and is analogous to them stating that all guests have been accounted for. Threads are instantiated, ran, and continued in random order with Java's Random object. To test the implementation, I set a timer on the operation and tested with 10, 100, and 1000 guests. I then looked at the printed statements to ensure that the guest threads are in fact being properly created and ran, and that the order of this operation is properly random. I also tested the run time effeciency to ensure that the 100 guests runtime was below 1 second.

## Part 2
</br>

### Methodology

### Experimental Evaulation

## Running

1. Pull repo
2. Navigate to local repo directory
3. Run command "javac Main.java"
4. Run command "java Main"
5. You will be prompted for the number of guests in part 1 and part 2
6. Actions of the guests will be printed to the screen for each part
