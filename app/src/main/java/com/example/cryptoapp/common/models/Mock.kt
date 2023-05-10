package com.example.cryptoapp.common.models

class Mock {

    companion object {

        fun mockData(): MutableList<Asset>{
            val list = mutableListOf<Asset>()
            list.add(
                Asset(
                    "BTC",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "ETH",
                    "Ethereum",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "BNB",
                    "BNB",
                    "",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "BTC",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "ETH",
                    "Ethereum",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "BNB",
                    "BNB",
                    "",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "BTC",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "ETH",
                    "Ethereum",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png",
                    0.0,
                    0.0
                )
            )
            list.add(
                Asset(
                    "BNB",
                    "BNB",
                    "",
                    0.0,
                    0.0
                )
            )
            return list
        }
    }
}
