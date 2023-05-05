package com.example.cryptoapp.common.models

class Mock {

    companion object {

        fun mockData(): MutableList<SearchAsset>{
            val list = mutableListOf<SearchAsset>()
            list.add(
                SearchAsset(
                    "BTC",
                    "Bitcoin",
                    "",
                )
            )
            list.add(
                SearchAsset(
                    "ETH",
                    "Ethereum",
                    "",
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
                    "",
                )
            )
            list.add(
                SearchAsset(
                    "ETH",
                    "Ethereum",
                    "",
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
                    "",
                )
            )
            list.add(
                SearchAsset(
                    "ETH",
                    "Ethereum",
                    "",
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
        fun mockData2(): ArrayList<SearchAsset> {
            val list = ArrayList<SearchAsset>()
            list.add(
                SearchAsset(
                    "BTC",
                    "Bitcoin",
                    "",
                )
            )
            list.add(
                SearchAsset(
                    "ETH",
                    "Ethereum",
                    "",
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
