describe('Pets Controller', function () {
    var $httpBackend, $rootScope, createController;

    // Set up the module
    beforeEach(module('app'));

    beforeEach(inject(function($injector) {
        // Set up the mock http service responses
        $httpBackend = $injector.get('$httpBackend');

        // Get hold of a scope (i.e. the root scope)
        $rootScope = $injector.get('$rootScope');
        // The $controller service is used to create instances of controllers
        var $controller = $injector.get('$controller');

        createController = function() {
            return $controller('PetsController', {'$scope' : $rootScope });
        };
    }));


    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('should save a pet', function () {
        createController();
        $rootScope.pet = {
            "id": "123",
            "name": "one"
        };
        var pet = {
            "id": "123",
            "name": "one"
        };
        var pets = [{"id":"321","name":"two"}];
        var categories = [{"id":"1", "name":"cat1"}];

        $httpBackend.expectGET('/getPets').respond(200, pets);
        $httpBackend.expectGET('/getCategories').respond(200, categories);
        $httpBackend.expectPOST('/pet', $rootScope.pet).respond(200, pet);

        $rootScope.savePet($rootScope.pet);
        $httpBackend.flush();
        expect($rootScope.pets[0].name).toBe('two');
        expect($rootScope.pets[1].name).toBe('one');
        expect($rootScope.categories).toEqual(categories);
    });
});
