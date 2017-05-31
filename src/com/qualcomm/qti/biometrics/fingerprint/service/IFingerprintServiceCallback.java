package com.qualcomm.qti.biometrics.fingerprint.service;

import android.os.*;

public interface IFingerprintServiceCallback extends IInterface
{
    void onEnrolled(final int p0, final int p1) throws RemoteException;
    
    void onError(final int p0) throws RemoteException;
    
    void onMatched(final int p0, final String p1) throws RemoteException;
    
    void onRemoved(final int p0) throws RemoteException;
    
    void onStatus(final int p0, final byte[] p1) throws RemoteException;
    
    public abstract static class Stub extends Binder implements IFingerprintServiceCallback
    {
        private static final String DESCRIPTOR = "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback";
        static final int TRANSACTION_onEnrolled = 5;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onMatched = 1;
        static final int TRANSACTION_onRemoved = 2;
        static final int TRANSACTION_onStatus = 4;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
        }
        
        public static IFingerprintServiceCallback asInterface(final IBinder binder) {
            if (binder == null) {
                return null;
            }
            final IInterface queryLocalInterface = binder.queryLocalInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
            if (queryLocalInterface != null && queryLocalInterface instanceof IFingerprintServiceCallback) {
                return (IFingerprintServiceCallback)queryLocalInterface;
            }
            return new Proxy(binder);
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(final int n, final Parcel parcel, final Parcel parcel2, final int n2) throws RemoteException {
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                case 1598968902: {
                    parcel2.writeString("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    this.onMatched(parcel.readInt(), parcel.readString());
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    this.onRemoved(parcel.readInt());
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    this.onError(parcel.readInt());
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    this.onStatus(parcel.readInt(), parcel.createByteArray());
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    this.onEnrolled(parcel.readInt(), parcel.readInt());
                    return true;
                }
            }
        }
        
        private static class Proxy implements IFingerprintServiceCallback
        {
            private IBinder mRemote;
            
            Proxy(final IBinder mRemote) {
                this.mRemote = mRemote;
            }
            
            public IBinder asBinder() {
                return this.mRemote;
            }
            
            public String getInterfaceDescriptor() {
                return "com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback";
            }
            
            @Override
            public void onEnrolled(final int n, final int n2) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    obtain.writeInt(n);
                    obtain.writeInt(n2);
                    this.mRemote.transact(5, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public void onError(final int n) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    obtain.writeInt(n);
                    this.mRemote.transact(3, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public void onMatched(final int n, final String s) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    obtain.writeInt(n);
                    obtain.writeString(s);
                    this.mRemote.transact(1, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public void onRemoved(final int n) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    obtain.writeInt(n);
                    this.mRemote.transact(2, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public void onStatus(final int n, final byte[] array) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qualcomm.qti.biometrics.fingerprint.service.IFingerprintServiceCallback");
                    obtain.writeInt(n);
                    obtain.writeByteArray(array);
                    this.mRemote.transact(4, obtain, (Parcel)null, 1);
                }
                finally {
                    obtain.recycle();
                }
            }
        }
    }
}
