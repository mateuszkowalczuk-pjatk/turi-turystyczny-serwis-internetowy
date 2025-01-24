import React, { useEffect, useState } from 'react'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { useStates } from '../../../hooks/shared/useStates.ts'
import { useRedirectEvery } from '../../../hooks/shared/useRedirect.ts'
import Input from '../../../components/Shared/Controls/Input'
import Label from '../../../components/Shared/Controls/Label'
import PersonalPart from '../../../components/Shared/Personal/PersonalPart'
import PersonalPanel from '../../../components/Shared/Personal/PersonalPanel'
import PersonalInput from '../../../components/Shared/Personal/PersonalInput'
import PersonalGender from '../../../components/Shared/Personal/PersonalGender'
import ProfileButton from '../../../components/Profile/ProfileButton'
import { Account, Address, Gender } from '../../../types'
import { addressService } from '../../../services/addressService.ts'
import { accountService } from '../../../services/accountService.ts'
import styles from './ProfilePersonalPage.module.css'

interface FormData {
    firstName: string | null
    lastName: string | null
    birthDay: number | null
    birthMonth: number | null
    birthYear: number | null
    gender: Gender | null
    phoneNumber: string | null
    address: {
        addressId: number | null
        country: string | null
        city: string | null
        zipCode: string | null
        street: string | null
        buildingNumber: string | null
        apartmentNumber: number | null
    }
}

const ProfilePersonalPage = () => {
    const { t, navigate } = useHooks()
    const { isAuthenticated } = useStates()
    const [formData, setFormData] = useState<FormData>({
        firstName: null,
        lastName: null,
        birthDay: null,
        birthMonth: null,
        birthYear: null,
        gender: null,
        phoneNumber: null,
        address: {
            addressId: null,
            country: null,
            city: null,
            zipCode: null,
            street: null,
            buildingNumber: null,
            apartmentNumber: null
        }
    })
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    useRedirectEvery([!isAuthenticated], '/')

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true)
            try {
                const accountResponse = await accountService.getById()
                if (accountResponse.status === 200) {
                    const accountData: Account = await accountResponse.json()

                    let addressData: Address | null = null
                    if (accountData.addressId) {
                        const addressResponse = await addressService.getById(accountData.addressId)
                        if (addressResponse.status === 200) addressData = await addressResponse.json()
                    }
                    let birthDay = null
                    let birthMonth = null
                    let birthYear = null

                    if (accountData.birthDate) {
                        const birthDate = new Date(accountData.birthDate)
                        birthDay = birthDate.getDate()
                        birthMonth = birthDate.getMonth() + 1
                        birthYear = birthDate.getFullYear()
                    }

                    setFormData({
                        firstName: accountData.firstName,
                        lastName: accountData.lastName,
                        birthDay: birthDay,
                        birthMonth: birthMonth,
                        birthYear: birthYear,
                        gender: accountData.gender.toString() === 'MALE' ? Gender.MALE : Gender.FEMALE,
                        phoneNumber: accountData.phoneNumber,
                        address: {
                            addressId: addressData?.addressId || null,
                            country: addressData?.country || null,
                            city: addressData?.city || null,
                            zipCode: addressData?.zipCode || null,
                            street: addressData?.street || null,
                            buildingNumber: addressData?.buildingNumber || null,
                            apartmentNumber: addressData?.apartmentNumber || null
                        }
                    })
                }
            } finally {
                setLoading(false)
            }
        }

        fetchData().catch((error) => error)
    }, [isAuthenticated, navigate, t])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setError(null)
        const { name, value } = e.target
        const parsedValue = ['birthDay', 'birthMonth', 'birthYear', 'apartmentNumber'].includes(name)
            ? value !== ''
                ? Number(value)
                : null
            : value === ''
              ? null
              : value

        setFormData((prevData) => {
            if (name.startsWith('address.')) {
                const addressField = name.split('.')[1]
                return {
                    ...prevData,
                    address: {
                        ...prevData.address,
                        [addressField]: parsedValue
                    }
                }
            } else {
                return {
                    ...prevData,
                    [name]: parsedValue
                }
            }
        })
    }

    const handleGenderChange = (gender: Gender) => {
        setFormData((prevData) => ({
            ...prevData,
            gender: gender
        }))
    }

    const handleUpdateAccount = async () => {
        setLoading(true)
        setError(null)

        if (
            !formData.firstName ||
            !formData.lastName ||
            formData.birthDay === null ||
            formData.birthMonth === null ||
            formData.birthYear === null ||
            formData.gender === null ||
            !formData.phoneNumber ||
            !formData.address.country ||
            !formData.address.city ||
            !formData.address.zipCode ||
            !formData.address.street ||
            !formData.address.buildingNumber
        ) {
            setError(t('signup-personal.error-missing-fields'))
            setLoading(false)
            return
        }

        if (
            formData.birthDay < 1 ||
            formData.birthDay > 31 ||
            formData.birthMonth < 1 ||
            formData.birthMonth > 12 ||
            formData.birthYear < 1900 ||
            formData.birthYear > new Date().getFullYear()
        ) {
            setError(t('signup-personal.error-invalid-birthdate'))
            setLoading(false)
            return
        }

        const zipCodeRegex = /^\d{2}-\d{3}$/
        if (!zipCodeRegex.test(formData.address.zipCode)) {
            setError(t('signup-personal.error-invalid-zipcode'))
            setLoading(false)
            return
        }

        const phoneRegex = /^\d{9,15}$/
        if (!phoneRegex.test(formData.phoneNumber)) {
            setError(t('signup-personal.error-invalid-phone-number'))
            setLoading(false)
            return
        }

        if (
            formData.address.apartmentNumber &&
            (isNaN(Number(formData.address.apartmentNumber)) || formData.address.apartmentNumber < 0)
        ) {
            setError(t('signup-personal.error-invalid-apartment-number'))
            setLoading(false)
            return
        }

        try {
            const phoneNumberCheckResponse = await accountService.isPhoneNumberExists(formData.phoneNumber)
            if (phoneNumberCheckResponse.status === 200) {
                const result = await phoneNumberCheckResponse.json()
                if (result) {
                    setError(t('signup-personal.error-phone-number-exists'))
                    setLoading(false)
                    return
                }
            } else {
                setError(t('signup-personal.error-general'))
                setLoading(false)
                return
            }

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
                    if (addressCreateResponse.status !== 200) {
                        setError(t('signup-personal.error-general'))
                        setLoading(false)
                        return
                    }
                    const address = await addressCreateResponse.json()
                    formData.address.addressId = address.addressId
                }
            } else {
                setError(t('signup-personal.error-general'))
                setLoading(false)
                return
            }

            const birthDate = `${formData.birthYear.toString().padStart(4, '0')}-${formData.birthMonth.toString().padStart(2, '0')}-${formData.birthDay.toString().padStart(2, '0')}`

            if (formData.address.addressId) {
                await accountService.update({
                    addressId: formData.address.addressId,
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    birthDate: new Date(birthDate),
                    phoneNumber: formData.phoneNumber,
                    gender: formData.gender
                })
            }
        } catch (error) {
            setError(t('signup-personal.error-general'))
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className={styles.page}>
            <PersonalPart
                firstPanel={
                    <PersonalPanel
                        label={<Label text={t('signup-personal.name-surname')} />}
                        firstInput={
                            <Input
                                type={'text'}
                                name={'firstName'}
                                placeholder={t('signup-personal.name')}
                                value={formData.firstName}
                                onChange={handleChange}
                                minLength={3}
                                maxLength={50}
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
                                minLength={3}
                                maxLength={50}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                secondPanel={
                    <PersonalPanel
                        label={<Label text={t('signup-personal.birthdate')} />}
                        firstInput={
                            <Input
                                type={'number'}
                                name={'birthDay'}
                                placeholder={t('signup-personal.day')}
                                value={formData.birthDay === null ? undefined : formData.birthDay}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        secondInput={
                            <Input
                                type={'number'}
                                name={'birthMonth'}
                                placeholder={t('signup-personal.month')}
                                value={formData.birthMonth === null ? undefined : formData.birthMonth}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        thirdInput={
                            <Input
                                type={'number'}
                                name={'birthYear'}
                                placeholder={t('signup-personal.year')}
                                value={formData.birthYear === null ? undefined : formData.birthYear}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                option={
                    <PersonalPanel
                        label={<Label text={t('signup-personal.gender')} />}
                        firstInput={
                            <PersonalGender
                                gender={formData.gender}
                                handleGenderChange={handleGenderChange}
                            />
                        }
                    />
                }
            />
            <PersonalPart
                firstPanel={
                    <PersonalPanel
                        label={<Label text={t('signup-personal.phone-number')} />}
                        firstInput={
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
                    />
                }
                secondPanel={
                    <PersonalPanel
                        label={<Label text={t('signup-personal.address')} />}
                        firstInput={
                            <Input
                                type={'text'}
                                name={'address.country'}
                                placeholder={t('signup-personal.country')}
                                value={formData.address.country || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        secondInput={
                            <Input
                                type={'text'}
                                name={'address.city'}
                                placeholder={t('signup-personal.city')}
                                value={formData.address.city || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        thirdInput={
                            <Input
                                type={'text'}
                                name={'address.zipCode'}
                                placeholder={t('signup-personal.zipcode')}
                                value={formData.address.zipCode || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        fourthInput={
                            <Input
                                type={'text'}
                                name={'address.street'}
                                placeholder={t('signup-personal.street')}
                                value={formData.address.street || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        fifthInput={
                            <Input
                                type={'text'}
                                name={'address.buildingNumber'}
                                placeholder={t('signup-personal.building')}
                                value={formData.address.buildingNumber || ''}
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                        sixthInput={
                            <PersonalInput
                                type={'number'}
                                name={'address.apartmentNumber'}
                                placeholder={t('signup-personal.apartment')}
                                value={
                                    formData.address.apartmentNumber === null
                                        ? undefined
                                        : formData.address.apartmentNumber
                                }
                                onChange={handleChange}
                                required={true}
                                disabled={loading}
                            />
                        }
                    />
                }
                option={
                    <ProfileButton
                        handleSave={handleUpdateAccount}
                        error={error}
                    />
                }
            />
        </div>
    )
}

export default ProfilePersonalPage
