import React, { useState, useEffect } from "react";

import { getAllProducts, getSelectedProduct, addToStocks, changeProductPrice } from "../../services/user.service";
import { insertCoin, getMachineState, dispenseProductAndReturnChange, refund, reset, collectMoney } from "../../services/machine.service";
import IUser from '../../types/user.type';
import * as AuthService from "../../services/auth.service";
import Swal from 'sweetalert2';
import { title } from "process";
import './Home.css';
import { CoinType } from "../../types/coin.type";
import { couldStartTrivia } from "typescript";
import { VendingMachine } from "../../types/VendingMachine";

export interface ProductStockQueryResponse {
  productStockId: number,
  count: number,
  productId: number,
  name: string,
  price: number,
}

const Home: React.FC = () => {
  const [productStockQueryResponse, setProductStockQueryResponse] = useState<ProductStockQueryResponse[]>([])
  const [loading, setLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState<IUser | undefined>(undefined);
  const [machineState, setMachineState] = useState<VendingMachine>();

  useEffect(() => {
    const fetchUserData = async () => {
      const updatedUser = await AuthService.getCurrentUser();

      if (updatedUser) {
        setCurrentUser(updatedUser);
      }

      getMachineState()
        .then((response) => {
          setMachineState(response.data);
        })
        .catch((error) => {
          const _content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
          setLoading(true);
          setProductStockQueryResponse(_content);
        });

      getAllProducts()
        .then((response) => {
          setProductStockQueryResponse(response.data);
          setLoading(false);
        })
        .catch((error) => {
          const _content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
          setLoading(true);
          setProductStockQueryResponse(_content);
        });
    };

    fetchUserData();
  }, []);

  const handleApiResponse = (response: any, successMessage: string) => {
    if (response.status === 200) {
      Swal.fire({ title: successMessage, icon: 'success' }).then((result) => {
        window.location.reload();
      });
    } else if (response.response && response.response.data && response.response.data.message) {
      Swal.fire({ title: response.response.data.message, icon: 'error' });
    }
  };

  const handleSelectProduct = async (productId: number) => {
    if (currentUser) {
      if (machineState) {
        getSelectedProduct(productId).then(
          (response) => {
            if (response.data) {
              const product = response.data;
              if (product.count > 0) {
                if (machineState.tempMoney >= product.price) {
                  Swal.fire({
                    title: 'Confirmation',
                    text: 'Do you want to continue with this purchase?',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonText: 'Yes',
                    cancelButtonText: 'No',
                  }).then((result) => {
                    if (result.isConfirmed) {
                      dispenseProductAndReturnChange(productId, currentUser.id).then((response) => {
                        handleApiResponse(response, response.data.message);
                      }).catch((error) => {
                        handleApiResponse(error, error.response.data.message);
                      });
                    }
                  });
                } else {
                  Swal.fire({ title: 'Please insert coin to get this product!' });
                }
              } else {
                Swal.fire({ title: 'Product is out of stock!' });
              }
            }
            setLoading(false);
          }
        );
      } else {
        Swal.fire({ title: 'Machine state not available!' });
      }
    } else {
      Swal.fire({ title: 'Log in to select a product!' });
    }
  };

  const handleRefund = () => {
    Swal.fire({
      title: 'Confirmation',
      text: 'Do you want to refund money?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        refund(currentUser?.id).then((response) => {
          handleApiResponse(response, response.data.message);
        }).catch((error) => {
          handleApiResponse(error, error.response.data.message);
        });
      }
    });
  }

  const handleInsertCoin = (coinType: CoinType) => {
    if (currentUser) {
      if (currentUser.money >= coinType) {
        Swal.fire({
          title: 'Confirmation',
          text: 'Do you want to insert ' + coinType + ' TL?',
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
        }).then((result) => {
          if (result.isConfirmed) {
            insertCoin(coinType, currentUser.id).then((response) => {
              handleApiResponse(response, response.data.message);
            }).catch((error) => {
              handleApiResponse(error, error.response.data.message);
            });
          }
        });
      } else {
        Swal.fire({ title: 'User does not have ' + coinType + ' TL to insert' });
      }
    } else {
      Swal.fire({ title: 'Log in to insert coin!' });
    }
  };

  const handleReset = () => {
    Swal.fire({
      title: 'Confirmation',
      text: 'Do you want to reset machine?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        reset().then((response) => {
          handleApiResponse(response, response.data.message);
        }).catch((error) => {
          handleApiResponse(error, error.response.data.message);
        });
      }
    });
  }

  const handleCollectMoney = () => {
    Swal.fire({
      title: 'Confirmation',
      text: 'Do you want to collect all money?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        collectMoney(currentUser?.id).then((response) => {
          handleApiResponse(response, response.data.message);
        }).catch((error) => {
          handleApiResponse(error, error.response.data.message);
        });
      }
    });
  }

  const handleAddToStocks = async () => {
    if (currentUser) {
      const { value: formValues } = await Swal.fire({
        title: 'Add products to stock',
        html: `
          <select id="swal-select" class="swal2-select">
            ${productStockQueryResponse.map((product) => `
              <option value="${product.productStockId}">${product.name}</option>
            `).join('')}
          </select>
          <input id="swal-input" class="swal2-input" type="number" placeholder="Quantity">
        `,
        focusConfirm: false,
        showCancelButton: true,
        preConfirm: () => {
          const selectInput = document.getElementById('swal-select') as HTMLSelectElement;
          const input = document.getElementById('swal-input') as HTMLInputElement;
          const productId = selectInput.value;
          const quantity = input.value;
          return { productId, quantity };
        }
      });

      if (formValues) {
        const { productId, quantity } = formValues;
        console.log(productId, quantity);

        const selectedProduct = productStockQueryResponse.find(product => product.productStockId === Number(productId));

        Swal.fire({
          title: 'Confirmation',
          text: `Add ${quantity} of ${selectedProduct?.name} to stock?`,
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
        }).then((result) => {
          if (result.isConfirmed) {
            addToStocks(productId, quantity).then((response) => {
              handleApiResponse(response, response.data.message);
            }).catch((error) => {
              handleApiResponse(error, error.response.data.message);
            });
          }
        });
      }
    } else {
      Swal.fire({ title: 'You do not have permission to perform this action!' });
    }
  };

  const handleChangeProductPrice = async () => {
    if (currentUser) {
      const { value: formValues } = await Swal.fire({
        title: 'Change price of a product',
        html: `
          <select id="swal-select" class="swal2-select">
            ${productStockQueryResponse.map((product) => `
              <option value="${product.productStockId}">${product.name}</option>
            `).join('')}
          </select>
          <input id="swal-input" class="swal2-input" type="number" placeholder="New price">
        `,
        focusConfirm: false,
        showCancelButton: true,
        preConfirm: () => {
          const selectInput = document.getElementById('swal-select') as HTMLSelectElement;
          const input = document.getElementById('swal-input') as HTMLInputElement;
          const productId = selectInput.value;
          const newPrice = input.value;
          return { productId, newPrice };
        }
      });

      if (formValues) {
        const { productId, newPrice } = formValues;

        const selectedProduct = productStockQueryResponse.find(product => product.productStockId === Number(productId));

        Swal.fire({
          title: 'Confirmation',
          text: `Change the price of ${selectedProduct?.name} from ${selectedProduct?.price} TL to ${newPrice} TL?`,
          icon: 'question',
          showCancelButton: true,
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
        }).then((result) => {
          if (result.isConfirmed) {
            changeProductPrice(productId, newPrice).then((response) => {
              handleApiResponse(response, response.data.message);
            }).catch((error) => {
              handleApiResponse(error, error.response.data.message);
            });
          }
        });
      }
    } else {
      Swal.fire({ title: 'You do not have permission to perform this action!' });
    }
  }

  const isCurrentUserAdmin = () => {
    return currentUser && currentUser.roles?.includes("ROLE_ADMIN");
  }

  return (
    <div className="container">
      {loading ? (
        <p>Loading...</p>
      ) : (
        <div className="product-grid">
          <div className="product-side">
            {productStockQueryResponse.map((product) => (
              <div key={product.productStockId} className="product">
                <div className="product-info">
                  <h4>{product.name}</h4>
                  <p>Price: {product.price} TL</p>
                  <p>{product.count} in stock</p>
                  <button onClick={() => handleSelectProduct(product.productStockId)}>
                    Select
                  </button>
                </div>
              </div>
            ))}
          </div>
          <div className="balance-side">
            <div className="user-balance">
              <h5>User Balance:</h5>
              <p>{currentUser?.money} TL</p>
            </div>
            {machineState ? (
              <div className="user-balance">
                <h5>Loaded in machine:</h5>
                <p>{machineState.tempMoney} TL</p>
                {currentUser ? (
                  <button className="refund-button" onClick={() => handleRefund()}>
                    Refund money
                  </button>
                ) : null}
              </div>
            ) : (
              <div className="user-balance">
                <h5>Loaded in machine:</h5>
                <p>Machine state not available</p>
              </div>
            )}

            <div className="insert-coin">
              <h5> Insert Coin </h5>
              <button className="coin-button" onClick={() => handleInsertCoin(CoinType.ONE)}>
                1 TL
              </button>
              <button className="coin-button" onClick={() => handleInsertCoin(CoinType.FIVE)}>
                5 TL
              </button>
              <button className="coin-button" onClick={() => handleInsertCoin(CoinType.TEN)}>
                10 TL
              </button>
              <button className="coin-button" onClick={() => handleInsertCoin(CoinType.TWENTY)}>
                20 TL
              </button>
            </div>

            {isCurrentUserAdmin() && (
              <div className="machine-operations">
                <h5> Machine Operations </h5>
                <button className="operation-button" onClick={() => handleReset()}>
                  Reset
                </button>
                <button className="operation-button" onClick={() => handleCollectMoney()}>
                  Collect Money
                </button>
                <button className="operation-button" onClick={() => handleAddToStocks()}>
                  Add to Stocks
                </button>
                <button className="operation-button" onClick={() => handleChangeProductPrice()}>
                  Change Product Price
                </button>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default Home;
