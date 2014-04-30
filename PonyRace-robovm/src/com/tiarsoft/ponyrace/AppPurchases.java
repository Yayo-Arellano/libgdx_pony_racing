package com.tiarsoft.ponyrace;


public class AppPurchases extends RobovmLauncher {

//	private InAppPurchaseManager iapManager;
//	private Map<String, SKProduct> appStoreProducts;
//
//	static private final String SKU_50MIL = "DragRace_buy_50mil";

	// @Override
	// public boolean didFinishLaunching(UIApplication application,
	// NSDictionary<NSString, ?> launchOptions) {
	// boolean result = super.didFinishLaunching(application, launchOptions);
	//
	// iapManager = new InAppPurchaseManager(new InAppPurchaseListener() {
	//
	// @Override
	// public void transcationRestored(SKPaymentTransaction transaction) {
	// }
	//
	// @Override
	// public void transactionFailed(SKPaymentTransaction transaction) {
	//
	// }
	//
	// @Override
	// public void transactionCompleted(SKPaymentTransaction transaction) {
	// String productId = transaction.getPayment()
	// .getProductIdentifier().toString();
	//
	// if (productId.equals(SKU_50MIL)) {
	// Settings.coinsTotal += 50000;
	// }
	// Settings.save();
	//
	// }
	//
	// @Override
	// public void productsReceived(SKProduct[] products) {
	// appStoreProducts = new HashMap<String, SKProduct>();
	// for (int i = 0; i < products.length; i++) {
	// appStoreProducts.put(products[i].getProductIdentifier()
	// .toString(), products[i]);
	// Gdx.app.log("InAppPurchases",
	// products[i].getProductIdentifier() + "");
	// Gdx.app.log("InAppPurchases", products[i].description());
	// }
	// }
	//
	// @Override
	// public void productsRequestFailed(SKRequest request, NSError error) {
	//
	// }
	//
	// });
	//
	// iapManager.requestProducts(SKU_50MIL);
	// return result;
	// }
	//
	// @Override
	// public void buy50milCoins() {
	// if (iapManager.canMakePayments() && appStoreProducts != null) {
	// iapManager.purchaseProduct(appStoreProducts.get(SKU_50MIL));
	// }
	// }

}
