package com.example.cryptoapp.common.models

class Mock {

    companion object {

        fun mockData(): MutableList<SearchAsset>{
            val list = mutableListOf<SearchAsset>()
            list.add(
                SearchAsset(
                    "BTC",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                )
            )
            list.add(
                SearchAsset(
                    "ETH",
                    "Ethereum",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png",
                )
            )
            list.add(
                SearchAsset(
                    "BNB",
                    "BNB",
                    "",
                )
            )
            list.add(
                SearchAsset(
                    "BTC",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                )
            )
            list.add(
                SearchAsset(
                    "ETH",
                    "Ethereum",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png",
                )
            )
            list.add(
                SearchAsset(
                    "BNB",
                    "BNB",
                    "",
                )
            )
            list.add(
                SearchAsset(
                    "BTC",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                )
            )
            list.add(
                SearchAsset(
                    "ETH",
                    "Ethereum",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png",
                )
            )
            list.add(
                SearchAsset(
                    "BNB",
                    "BNB",
                    "",
                )
            )
            return list
        }
    }
}
