var app = angular.module('app', ['ngDialog']);
app.directive('customOnChange', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var onChangeFunc = scope.$eval(attrs.customOnChange);
            element.bind('change', onChangeFunc);
        }
    };
});

app.directive('petList', function () {
    return {
        restrict: 'E',
        templateUrl: "pet-list.html"
    }
});

app.directive('newCategory', function () {
    return {
        restrict: 'E',
        templateUrl: "new-category.html"
    }
});

app.directive('petCategories', function () {
    return {
        restrict: 'E',
        templateUrl: "pet-categories.html"
    }
});

app.directive('petTags', function () {
    return {
        restrict: 'E',
        templateUrl: "pet-tags.html"
    }
});

app.directive('newPet', function () {
    return {
        restrict: 'E',
        templateUrl: "new-pet.html"
    }
});

app.directive('imageList', function () {
    return {
        restrict: 'E',
        templateUrl: 'image-list.html'
    }
});

app.directive('viewPet', function () {
    return {
        restrict: 'E',
        templateUrl: 'view-pet.html'
    }
});

app.directive("petTabs", function () {
    return {
        restrict: "E",
        templateUrl: "pet-tabs.html",
        controller: function () {
            this.tab = 1;

            this.isSet = function (checkTab) {
                return this.tab === checkTab;
            };

            this.setTab = function (activeTab) {
                this.tab = activeTab;
            };
        },
        controllerAs: "tab"
    };
});


//we use this service to share data between controllers
app.service('sharePetService', function () {
    var petId = {};

    var addPetId = function (p) {
        petId = p;
    };

    var getPetId = function () {
        return petId;
    };

    return {
        addPetId: addPetId,
        getPetId: getPetId
    };
});


app.controller('ViewPetController', function ($scope, $http, ngDialog, sharePetService) {
    this.petId = sharePetService.getPetId();
    $scope.pet = {};
    $http.get('/pet/' + this.petId).then(function (response) {
        $scope.pet = response.data;
    });

    $scope.closePetDetails = function () {
        $scope.pet = {};
        ngDialog.close();
    };
});

app.controller('PetsController', function ($scope, $http, $log, sharePetService, ngDialog) {
    $scope.pets = [{}];

    function resetPet() {
        $scope.pet = {
            "photoUrls": [],
            "category": {},
            "tags": [{}]
        };
    }

    resetPet();

    function loadPets() {
        $http.get('/getPets').then(function (response) {
            $scope.pets = response.data;
        });
    }

    loadPets();

    $scope.removeImg = function(url) {
        var index = $scope.pet.photoUrls.indexOf(url);
        if(index > -1) {
            $scope.pet.photoUrls.splice(index, 1);
        }
    };

    $scope.viewPetDetails = function (pet) {
        sharePetService.addPetId(pet.id);
        ngDialog.open({template: 'templateId'});
    };


    $scope.category = {};
    $scope.categories = [{}];

    function loadCategories() {
        $http.get('/getCategories').then(function (response) {
            $scope.categories = response.data;
        });
    }

    loadCategories();

    $scope.saveCategory = function () {
        $http.post('/category', $scope.category).then(function (response) {
            loadCategories();
            $scope.category = {};
        });
    };

    $scope.deleteCategory = function (categ) {
        $http.delete('/category/' + categ.id).success(function () {
            loadCategories();
        });
    };

    $scope.savePet = function () {
        $http.post('/pet', $scope.pet).then(function (response) {
            $scope.pets.push(response.data);
            resetPet();
        });
    };

    $scope.deletePet = function (pett) {
        $http.delete('/pet/' + pett.id).success(function () {
            loadPets();
        });
    };

    $scope.addImage = function () {
        var files = event.target;
        var f = files.files[0],
            r = new FileReader();
        r.onloadend = function (e) {
            var fd = new FormData();
            fd.append('file', f);
            $http.post('/image', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                if ($scope.pet.photoUrls.indexOf(response.data.response) === -1) {
                    $scope.pet.photoUrls.push(response.data.response);
                    $log.debug('success');
                } else {
                    $log.debug('image already assigned to pet');
                }
                files.value = null;
            }, function (response) {
                alert(response.data.message);
            });
        };
        r.readAsArrayBuffer(f);
    };

});