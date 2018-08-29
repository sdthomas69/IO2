package cev.blackFish

class SearchController extends BaseController {

    def searchService

    def index() {

        setParams(params)

        def searchResult = null

        if (params.q) searchResult = searchService.searchAll(params)

        render(template: "/search/searchResults", model: searchResult, params: params, layout: "main")
    }

    def remoteSearch() {

        setParams(params)

        def searchResult = null

        if (params.q) searchResult = searchService.searchAll(params)

        render(template: "/search/remoteSearch", model: searchResult, params: params)
    }

    def dropDownSearch() {

        params.max = "5"

        setParams(params)

        def searchResult = null

        if (params.q) searchResult = searchService.searchAll(params)

        render(template: "/search/simpleResultList", model: searchResult, params: params)
    }
}
