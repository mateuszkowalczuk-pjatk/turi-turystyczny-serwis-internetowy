import { useHooks } from '../shared/useHooks.ts'
import { useEffect, useState } from 'react'
import { Account } from '../../types'
import { userService } from '../../services/userService.ts'
import { accountService } from '../../services/accountService.ts'

export const useReservationPersonal = (): {
    firstName: string | null
    setFirstName: (value: ((prevState: string | null) => string | null) | string | null) => void
    lastName: string | null
    setLastName: (value: ((prevState: string | null) => string | null) | string | null) => void
    phoneNumber: string | null
    setPhoneNumber: (value: ((prevState: string | null) => string | null) | string | null) => void
    account: Account | null
    email: string | null
} => {
    const { navigate } = useHooks()
    const [account, setAccount] = useState<Account | null>(null)
    const [firstName, setFirstName] = useState<string | null>(null)
    const [lastName, setLastName] = useState<string | null>(null)
    const [phoneNumber, setPhoneNumber] = useState<string | null>(null)
    const [email, setEmail] = useState<string | null>(null)

    useEffect(() => {
        const fetchDate = async () => {
            const emailResponse = await userService.getEmail()
            if (emailResponse.status === 200) {
                const emailData: string = await emailResponse.text()
                setEmail(emailData)
            } else navigate(-1)
            const accountResponse = await accountService.getById()
            if (accountResponse.status === 200) {
                const accountData: Account = await accountResponse.json()
                setAccount(accountData)
                setFirstName(accountData.firstName || null)
                setLastName(accountData.lastName || null)
                setPhoneNumber(accountData.phoneNumber || null)
            }
        }
        fetchDate().catch((error) => error)
    }, [])

    return { account, firstName, setFirstName, lastName, setLastName, phoneNumber, setPhoneNumber, email }
}
