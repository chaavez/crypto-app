package com.example.cryptoapp.models

class Mock {

    companion object {
        fun mock() : ArrayList<Asset> {
            val list = ArrayList<Asset>()

            list.add(
                Asset(
                    "BTC",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    11515715.01,
                    9.0
                ))

            list.add(
                Asset(
                    "ETH",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    2500.0,
                    19.0
                ))

            list.add(
                Asset(
                    "ETH",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    2500.0,
                    19.0
                ))

            list.add(
                Asset(
                    "ETH",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    2500.0,
                    19.0
                ))

            list.add(
                Asset(
                    "ETH",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    2500.0,
                    19.0
                ))

            list.add(
                Asset(
                    "ETH",
                    "Bitcoin",
                    "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                    2500.0,
                    19.0
                ))
            return list
        }
    }
}