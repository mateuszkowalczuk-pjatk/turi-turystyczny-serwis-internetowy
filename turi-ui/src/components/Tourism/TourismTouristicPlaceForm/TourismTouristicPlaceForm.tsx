import React, { useEffect, useState } from 'react'
import TourismTouristicPlaceDetails from '../TourismTouristicPlaceDetails'
import TouristicPlaceGuaranteedServices from '../TouristicPlaceGuaranteedServices'
import TourismTouristicPlaceOwner from '../TourismTouristicPlaceOwner'
import TourismTouristicPlaceSave from '../TourismTouristicPlaceSave'
import { TouristicPlace, TouristicPlaceType } from '../../../types/touristicPlace.ts'
import PersonalPanel from '../../Shared/Personal/PersonalPanel'
import PersonalLabel from '../../Shared/Personal/PersonalLabel'
import Input from '../../Shared/Controls/Input'
import { useTranslation } from 'react-i18next'
import { userService } from '../../../services/userService.ts'
import { accountService } from '../../../services/accountService.ts'
import { touristicPlaceService } from '../../../services/touristicPlaceService.ts'
import { addressService } from '../../../services/addressService.ts'
import { Account, Address } from '../../../types'
import Checkbox from '../../Shared/Controls/Checkbox'
import TourismTouristicPlaceLabel from '../TourismTouristicPlaceLabel'
import TourismTouristicPlaceTypeSelect from '../TourismTouristicPlaceTypeSelect'
import TourismTouristicPlaceCheckbox from '../TourismTouristicPlaceCheckbox'
import { handle } from '../../../utils/handle.ts'
import { handleFormError } from '../../../utils/handleFormError.ts'
import { checkInOutTimeValidation } from '../../../utils/checkInOutTimeValidation.ts'
import styles from './TourismTouristicPlaceForm.module.css'

interface FormData {
    name: string
    touristicPlaceType: TouristicPlaceType
    description: string
    address: Address
    prepayment: boolean
    cancelReservation: boolean
    cancelReservationDays: number
    checkInTime: string
    checkOutTime: string
    information: string
    firstName: string
    lastName: string
    phoneNumber: string
    ownerDescription: string
}

const TourismTouristicPlaceForm = ({ touristicPlaceId }: { touristicPlaceId: number }) => {
    const { t } = useTranslation()
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    const [email, setEmail] = useState<string>('test')
    const [formData, setFormData] = useState<FormData>({
        name: '',
        touristicPlaceType: TouristicPlaceType.UNASSIGNED,
        description: '',
        address: {
            country: '',
            city: '',
            zipCode: '',
            street: '',
            buildingNumber: ''
        },
        prepayment: false,
        cancelReservation: false,
        cancelReservationDays: 0,
        checkInTime: '',
        checkOutTime: '',
        information: '',
        firstName: '',
        lastName: '',
        phoneNumber: '',
        ownerDescription: ''
    })

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setFormData((prevData) => {
            if (name.startsWith('address.')) {
                const field = name.split('.')[1]
                return {
                    ...prevData,
                    address: {
                        ...prevData.address,
                        [field]: value
                    }
                }
            }
            return {
                ...prevData,
                [name]: value
            }
        })
    }

    useEffect(() => {
        const fetchData = async () => {
            const emailResponse = await userService.getEmail()
            if (emailResponse.status === 200) {
                const emailData: string = await emailResponse.text()
                setEmail(emailData)
            } else return handleFormError(setError, setLoading, t('tourism.touristic-place-general-error'))
            const accountResponse = await accountService.getById()
            if (accountResponse.status === 200) {
                const accountData: Account = await accountResponse.json()
                const touristicPlaceResponse = await touristicPlaceService.getByPremiumId()
                if (touristicPlaceResponse.status === 200) {
                    const touristicPlaceData: TouristicPlace = await touristicPlaceResponse.json()
                    let address: Address = {
                        country: '',
                        city: '',
                        zipCode: '',
                        street: '',
                        buildingNumber: '',
                        apartmentNumber: undefined
                    }
                    if (touristicPlaceData.addressId != null) {
                        const addressResponse = await addressService.getById(touristicPlaceData.addressId)
                        const addressData: Address = await addressResponse.json()
                        address = {
                            country: addressData.country || '',
                            city: addressData.city || '',
                            zipCode: addressData.zipCode || '',
                            street: addressData.street || '',
                            buildingNumber: addressData.buildingNumber || '',
                            apartmentNumber: addressData.apartmentNumber
                        }
                    }
                    setFormData({
                        name: touristicPlaceData.name || '',
                        touristicPlaceType: touristicPlaceData.touristicPlaceType || TouristicPlaceType.UNASSIGNED,
                        description: touristicPlaceData.description || '',
                        address: {
                            country: address.country,
                            city: address.city,
                            zipCode: address.zipCode,
                            street: address.street,
                            buildingNumber: address.buildingNumber,
                            apartmentNumber: address.apartmentNumber
                        },
                        prepayment: touristicPlaceData.prepayment || false,
                        cancelReservation: touristicPlaceData.cancelReservationDays !== 0,
                        cancelReservationDays: touristicPlaceData.cancelReservationDays || 0,
                        checkInTime: handleCheckTime(
                            touristicPlaceData.checkInTimeFrom,
                            touristicPlaceData.checkInTimeTo
                        ),
                        checkOutTime: handleCheckTime(
                            touristicPlaceData.checkOutTimeFrom,
                            touristicPlaceData.checkOutTimeTo
                        ),
                        information: touristicPlaceData.information || '',
                        firstName: accountData.firstName || '',
                        lastName: accountData.lastName || '',
                        phoneNumber: accountData.phoneNumber || '',
                        ownerDescription: touristicPlaceData.ownerDescription || ''
                    })
                } else return handleFormError(setError, setLoading, t('tourism.touristic-place-general-error'))
            } else return handleFormError(setError, setLoading, t('tourism.touristic-place-general-error'))
        }
        fetchData().catch((error) => error)
    }, [t])

    const handleCheckTime = (from?: string, to?: string): string => {
        if (!from || !to) return ''
        const formatTime = (time: string) => {
            const hour = parseInt(time[0]) >= 0 && parseInt(time[0]) <= 9 ? '0' + time[0] : time[0]
            const minute = parseInt(time[1]) >= 0 && parseInt(time[1]) <= 9 ? '0' + time[1] : time[1]
            return hour + ':' + minute
        }
        return `${formatTime(from)} - ${formatTime(to)}`
    }

    const handleSave = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        if (!checkInOutTimeValidation(formData.checkInTime) || !checkInOutTimeValidation(formData.checkOutTime))
            return handleFormError(setError, setLoading, t('tourism.touristic-place-check-in-out-time'))

        let addressId = 0
        const addressCheckResponse = await accountService.isAddressExists(
            formData.address.country,
            formData.address.city,
            formData.address.zipCode,
            formData.address.street,
            formData.address.buildingNumber,
            formData.address.apartmentNumber ? formData.address.apartmentNumber.toString() : null
        )
        if (addressCheckResponse.status === 200) {
            const result = await addressCheckResponse.json()
            if (!result) {
                const addressCreateResponse = await addressService.create({
                    country: formData.address.country,
                    city: formData.address.city,
                    zipCode: formData.address.zipCode,
                    street: formData.address.street,
                    buildingNumber: formData.address.buildingNumber,
                    apartmentNumber:
                        formData.address.apartmentNumber === null ? undefined : formData.address.apartmentNumber
                })
                if (addressCreateResponse.status !== 200)
                    return handleFormError(setError, setLoading, t('tourism.touristic-place-general-error'))
                const address = await addressCreateResponse.json()
                addressId = address.addressId
            }
        } else return handleFormError(setError, setLoading, t('tourism.touristic-place-general-error'))

        const touristicPlaceResponse = await touristicPlaceService.updateDetails(touristicPlaceId, {
            premiumId: 0,
            addressId: addressId,
            touristicPlaceType: formData.touristicPlaceType,
            name: formData.name,
            description: formData.description,
            information: formData.information,
            ownerDescription: formData.ownerDescription,
            checkInTimeFrom: formData.checkInTime.split(' - ')[0],
            checkInTimeTo: formData.checkInTime.split(' - ')[1],
            checkOutTimeFrom: formData.checkOutTime.split(' - ')[0],
            checkOutTimeTo: formData.checkOutTime.split(' - ')[1],
            prepayment: formData.prepayment,
            cancelReservationDays: formData.cancelReservation ? formData.cancelReservationDays : 0
        })
        if (touristicPlaceResponse.status !== 200)
            return handleFormError(setError, setLoading, t('tourism.touristic-place-general-error'))

        const currentAccountResponse = await accountService.getById()
        const currentAccountData: Account = await currentAccountResponse.json()
        const accountResponse = await accountService.update({
            userId: currentAccountData.userId,
            addressId: currentAccountData.addressId,
            firstName: formData.firstName,
            lastName: formData.lastName,
            birthDate: currentAccountData.birthDate,
            phoneNumber: formData.phoneNumber,
            gender: currentAccountData.gender
        })
        if (accountResponse.status !== 200)
            return handleFormError(setError, setLoading, t('tourism.touristic-place-general-error'))
        setLoading(false)
    }

    return (
        <form
            className={styles.form}
            onSubmit={handleSave}
        >
            <div className={styles.touristicplace}>
                <TourismTouristicPlaceDetails
                    firstPanel={
                        <PersonalPanel
                            label={<PersonalLabel text={t('tourism.touristic-place-name-and-type')} />}
                            firstInput={
                                <Input
                                    type={'text'}
                                    name={'name'}
                                    placeholder={t('tourism.touristic-place-name-placeholder')}
                                    value={formData.name}
                                    onChange={handleChange}
                                    minLength={5}
                                    maxLength={50}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            secondInput={
                                <TourismTouristicPlaceTypeSelect
                                    value={formData.touristicPlaceType}
                                    onChange={(touristicPlaceType: TouristicPlaceType) =>
                                        setFormData((prevData) => ({
                                            ...prevData,
                                            touristicPlaceType: touristicPlaceType
                                        }))
                                    }
                                    disabled={loading}
                                />
                            }
                            touristicPlace
                        />
                    }
                    secondPanel={
                        <PersonalPanel
                            label={<PersonalLabel text={t('tourism.touristic-place-description')} />}
                            firstInput={
                                <Input
                                    type={'text'}
                                    name={'description'}
                                    placeholder={t('tourism.touristic-place-description')}
                                    value={formData.description}
                                    onChange={handleChange}
                                    minLength={5}
                                    maxLength={255}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            touristicPlace
                        />
                    }
                    thirdPanel={
                        <PersonalPanel
                            label={<PersonalLabel text={t('address.title')} />}
                            firstInput={
                                <Input
                                    type={'text'}
                                    name={'address.country'}
                                    placeholder={t('address.country')}
                                    value={formData.address.country}
                                    onChange={handleChange}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            secondInput={
                                <Input
                                    type={'text'}
                                    name={'address.city'}
                                    placeholder={t('address.city')}
                                    value={formData.address.city}
                                    onChange={handleChange}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            thirdInput={
                                <Input
                                    type={'text'}
                                    name={'address.zipCode'}
                                    placeholder={t('address.zipCode')}
                                    value={formData.address.zipCode}
                                    onChange={handleChange}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            fourthInput={
                                <Input
                                    type={'text'}
                                    name={'address.street'}
                                    placeholder={t('address.street')}
                                    value={formData.address.street}
                                    onChange={handleChange}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            fifthInput={
                                <Input
                                    type={'text'}
                                    name={'address.buildingNumber'}
                                    placeholder={t('address.buildingNumber')}
                                    value={formData.address.buildingNumber}
                                    onChange={handleChange}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            sixthInput={
                                <Input
                                    type={'number'}
                                    name={'address.apartmentNumber'}
                                    placeholder={t('address.apartmentNumber')}
                                    value={formData.address.apartmentNumber}
                                    onChange={handleChange}
                                    required={false}
                                    disabled={loading}
                                />
                            }
                        />
                    }
                    fourthPanel={
                        <PersonalPanel
                            label={<PersonalLabel text={t('tourism.touristic-place-check-in-and-check-out')} />}
                            firstInput={
                                <Input
                                    type={'text'}
                                    name={'checkInTime'}
                                    placeholder={t('tourism.touristic-place-check-in')}
                                    value={formData.checkInTime}
                                    onChange={handleChange}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            secondInput={
                                <Input
                                    type={'text'}
                                    name={'checkOutTime'}
                                    placeholder={t('tourism.touristic-place-check-out')}
                                    value={formData.checkOutTime}
                                    onChange={handleChange}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            touristicPlace
                        />
                    }
                    fifthPanel={
                        <PersonalPanel
                            label={<PersonalLabel text={t('tourism.touristic-place-reservation')} />}
                            firstInput={
                                <TourismTouristicPlaceCheckbox
                                    checkbox={
                                        <Checkbox
                                            checked={formData.prepayment}
                                            onChange={() =>
                                                setFormData((prevData) => ({
                                                    ...prevData,
                                                    prepayment: !prevData.prepayment
                                                }))
                                            }
                                            text={t('tourism.touristic-place-reservation-prepayment')}
                                        />
                                    }
                                />
                            }
                            secondInput={
                                <TourismTouristicPlaceCheckbox
                                    checkbox={
                                        <Checkbox
                                            checked={formData.cancelReservation}
                                            onChange={() =>
                                                setFormData((prevData) => ({
                                                    ...prevData,
                                                    cancelReservation: !prevData.cancelReservation,
                                                    cancelReservationDays: !prevData.cancelReservation
                                                        ? 0
                                                        : prevData.cancelReservationDays
                                                }))
                                            }
                                            text={t('tourism.touristic-place-reservation-cancel')}
                                        />
                                    }
                                />
                            }
                            thirdInput={
                                <Input
                                    type="number"
                                    name="cancelReservationDays"
                                    placeholder={t('tourism.touristic-place-reservation-days')}
                                    value={formData.cancelReservationDays}
                                    onChange={handleChange}
                                    required={false}
                                    disabled={!formData.cancelReservation || loading}
                                />
                            }
                            touristicPlace
                        />
                    }
                    sixthPanel={
                        <PersonalPanel
                            label={<PersonalLabel text={t('tourism.touristic-place-important-information')} />}
                            firstInput={
                                <Input
                                    type={'text'}
                                    name={'information'}
                                    placeholder={t('tourism.touristic-place-important-information')}
                                    value={formData.information}
                                    onChange={handleChange}
                                    minLength={5}
                                    maxLength={255}
                                    required={true}
                                    disabled={loading}
                                />
                            }
                            touristicPlace
                        />
                    }
                />
                <div className={styles.details}>
                    <TouristicPlaceGuaranteedServices touristicPlaceId={touristicPlaceId} />
                    <TourismTouristicPlaceOwner
                        firstPanel={
                            <PersonalPanel
                                label={<PersonalLabel text={t('signup-personal.name-surname')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'firstName'}
                                        placeholder={t('signup-personal.name')}
                                        value={formData.firstName}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'lastName'}
                                        placeholder={t('signup-personal.surname')}
                                        value={formData.lastName}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        secondPanel={
                            <PersonalPanel
                                label={<PersonalLabel text={t('tourism.touristic-place-contact')} />}
                                firstInput={<TourismTouristicPlaceLabel text={email} />}
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'phoneNumber'}
                                        placeholder={t('signup-personal.phone-number')}
                                        value={formData.phoneNumber}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        thirdPanel={
                            <PersonalPanel
                                label={<PersonalLabel text={t('tourism.touristic-place-description')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'ownerDescription'}
                                        placeholder={t('tourism.touristic-place-description')}
                                        value={formData.ownerDescription}
                                        onChange={handleChange}
                                        required={false}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                    />
                </div>
            </div>
            <TourismTouristicPlaceSave error={error} />
        </form>
    )
}

export default TourismTouristicPlaceForm
