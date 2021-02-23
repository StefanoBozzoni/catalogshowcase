# Catalog Showcase

Catalog showcase is a demo app that shows a simple catalog with a detail product page and implements several demo tests.

## Features
Catalog showcase implements several tests and is build upon MVVM architecture and coroutines.
It also uses endless scroll feature and all the tests can be run without internet connection because internally it uses a mockWebServer.
Unit test and Integrations Tests with coroutines are a new subject that is continuos evolution and the library that is used for test uses indeed an @ExperimentalCoroutinesApi annotation to annotate tests. It's not been easy to find the necessary documentation for to run this kind of tests and i come up with some solution that nobody has been spoken about in some scenarios.

## Limits and possible improvment
It has been build in 3 days and for this reason it doesn't show well on tablet, expecially the detail page.
Configuration changes are disabled with the "nosensor" tag in the manifest, and for this reason it could not work well on some devices, even if it's rare (that things can easily be imrpoved as well as the tablet thing).
The endless scroll feature could be implemented using the paging library but i used a well known method that is well tested and i was more confident with it, moreover the paging library is always subject to changes and maybe it could be not the right choice for now.
The implemented Endless scroll Listener can be used with a grid layout manager, and i would have liked to show a grid because i guess it would have been a better view, but i guess and i hope the purpouse of this project wasn't about graphic abilities, so i decided to keep it that way.

## Implemented tests:

### Unit tests

ViewModelTest 1 

This test check the viemodel code for getCatalog and getProduct methods, it does that mocking the underlying use cases providing json on their place, it uses the mocKK library that is similar to mockito but better suited for koin.
For this reasons this test checks only the viewmodel code, moreover the json should not be the json returned from the services, because they have to map the repository answer methods and not the remote layer.

ViewModelTest 2 

This tests check the getCatalog and getProduct methods using mockWebServer and so they tests all the chain from the servise response until the viemodel layer, they tests also livedata funcionality. Moreover i provided an example that check getCatalog behavior when the server answer "internal server error".

Interactor tests 

This tests check the uses cases classes (actually only getCatalogUseCase, but getProductUseCase is similar and strightforward), and it simulates HTTP_INTERNAL_ERROR.

### Integration Tests

Activity tests 

This tests check the main activity (CatalogSearchActivity) and the detail product activity (DetailActivity) , using the roboelectric enviroenment included in androidx.

VieModelTest 2 could be tought as an integration test too even if it uses android unit test technology

### UI Tests

ActivityInstrumentedTest and ActivityNetworErrorInstrumentedTest

this tests check all the functionality of the 2 application activities and fragments. The click on buttons, observes results, scrolls and click the recyclerview. Because they were strightforward i didn't wont to use more complicate IdlingResource statements but in a scenario i have used a tecnique for to wait recyclerview adapter data loading.


Hope you like it.

Thank you

Stefano


 
















